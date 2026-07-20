package net.meatwo310.softdeepslate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/** Resolves configured block and tag selectors into one immutable set for fast lookups. */
public final class ConfiguredBlockSet<T> {
    private final Supplier<? extends Iterable<String>> selectors;
    private final Resolver<T> resolver;
    private volatile Set<T> cache;

    public ConfiguredBlockSet(Supplier<? extends Iterable<String>> selectors, Resolver<T> resolver) {
        this.selectors = Objects.requireNonNull(selectors);
        this.resolver = Objects.requireNonNull(resolver);
    }

    public boolean contains(T value) {
        return get().contains(value);
    }

    public void invalidate() {
        cache = null;
    }

    private Set<T> get() {
        Set<T> cached = cache;
        if (cached == null) {
            synchronized (this) {
                cached = cache;
                if (cached == null) {
                    cached = build();
                    cache = cached;
                }
            }
        }
        return cached;
    }

    private Set<T> build() {
        Set<T> included = new HashSet<>();
        Set<T> excluded = new HashSet<>();
        Set<T> forced = new HashSet<>();

        for (String rawSelector : selectors.get()) {
            Selector selector = Selector.parse(rawSelector);
            if (selector == null) {
                Constants.LOGGER.warn("Invalid block selector in config: {}", rawSelector);
                continue;
            }

            Set<T> destination = switch (selector.priority()) {
                case INCLUDE -> included;
                case EXCLUDE -> excluded;
                case FORCE_INCLUDE -> forced;
            };
            resolve(selector, destination);
        }

        included.removeAll(excluded);
        included.addAll(forced);
        Set<T> resolved = Set.copyOf(included);

        if (resolved.isEmpty()) {
            Constants.LOGGER.warn("No valid blocks are configured for faster mining");
        } else {
            Constants.LOGGER.info(
                    "Cached {} blocks for faster mining ({} excluded, {} force-added)",
                    resolved.size(), excluded.size(), forced.size());
        }
        return resolved;
    }

    private void resolve(Selector selector, Set<T> destination) {
        Optional<? extends Iterable<T>> values = selector.tag()
                ? resolver.resolveTag(selector.id())
                : resolver.resolveBlock(selector.id()).map(Set::of);

        values.ifPresentOrElse(
                blocks -> blocks.forEach(destination::add),
                () -> Constants.LOGGER.warn(
                        "Unknown or invalid block {} in config: {}",
                        selector.tag() ? "tag" : "ID",
                        selector.displayName()));
    }

    private enum Priority {
        INCLUDE,
        EXCLUDE,
        FORCE_INCLUDE
    }

    private record Selector(Priority priority, boolean tag, String id) {
        private static Selector parse(String raw) {
            if (raw == null) {
                return null;
            }

            String value = raw.trim();
            if (value.isEmpty() || value.startsWith("!!!")) {
                return null;
            }

            Priority priority;
            if (value.startsWith("!!")) {
                priority = Priority.FORCE_INCLUDE;
                value = value.substring(2);
            } else if (value.startsWith("!")) {
                priority = Priority.EXCLUDE;
                value = value.substring(1);
            } else {
                priority = Priority.INCLUDE;
            }

            boolean tag = value.startsWith("#");
            if (tag) {
                value = value.substring(1);
            }
            if (value.isEmpty() || value.startsWith("!") || value.startsWith("#")) {
                return null;
            }
            return new Selector(priority, tag, value);
        }

        private String displayName() {
            return (tag ? "#" : "") + id;
        }
    }

    public interface Resolver<T> {
        Optional<T> resolveBlock(String id);

        Optional<? extends Iterable<T>> resolveTag(String id);
    }
}
