# Contributing

## Commit Message Convention

Follow [Conventional Commits](https://www.conventionalcommits.org/):

```
type(scope): description
```

### Scope rules for subprojects

Subprojects are the top-level directories corresponding to each platform/version (such as `26.1-fabric` or `1.20.1-forge`).

| Changes affect... | Scope |
|---|---|
| A single subproject | Use that subproject's directory name (e.g. `fix(26.1-fabric): ...`) |
| Multiple but not all subprojects | Include the relevant names (e.g. `feat(1.20.1-forge,1.21.1-neo): ...`) |
| All subprojects equally | Scope is optional |
| Root-level files only | Scope is optional |

### Valid types

`feat` · `fix` · `docs` · `style` · `refactor` · `perf` · `test` · `build` · `ci` · `chore` · `revert`

### Examples

```
feat(26.1-fabric): add config screen
fix(1.20.1-forge): resolve crash on startup
build: upgrade Gradle to 8.14
chore(1.18.2-forge,1.19.2-forge): bump forge version
```
