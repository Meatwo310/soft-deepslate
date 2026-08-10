# Soft Deepslate

## Publishing

The `Publish` GitHub Actions workflow copies an existing GitHub Release to
CurseForge and Modrinth. It uploads each main jar together with its sources jar;
it does not rebuild release artifacts.

The workflow is a dry run by default. Configure the `CURSEFORGE_TOKEN` and
`MODRINTH_TOKEN` repository secrets before disabling dry-run mode.

To inspect a release locally without API keys, authenticate the GitHub CLI and
run the publishing tasks from a checkout whose project matrix matches the
release:

```sh
gh auth status
nix develop -c ./gradlew publishMods -PpublishTag=v3.0.0 -PpublishDryRun=true
```

The plugin writes the files and metadata prepared for each destination under
`build/publishMods/`. Limit the dry run to one service with
`-PpublishDestination=curseforge` or `-PpublishDestination=modrinth`.
