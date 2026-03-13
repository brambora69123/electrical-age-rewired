# Contributing

This is an open source project. We appreciate any help from the community to improve the mod.

### Bugs or ideas for new items

Did you find a bug or do you have an idea how to improve the mod? We are happy to hear from you.

- [Discord](https://discord.gg/USruYk7pjW)
- [Bug Tracker](https://github.com/brambora69123/electrical-age-rewired/issues)

Contributions via [pull request](https://github.com/brambora69123/electrical-age-rewired/pulls) and [bug reports](https://github.com/brambora69123/electrical-age-rewired/issues) are welcome!
Please submit your pull requests to the **main** branch and use the GitHub issue tracker to report issues.

### Translations

Is the mod not available in your language or are some translations missing? You can change that by adding or modifying a translation.

Some translation strings might contain placeholders for runtime arguments in order to include numbers or other objects into the sentence. These are identified by **%N$**, where *N* is the number of the argument. A translation string should include these placeholders at the appropriate position in the text.

You can easily add new languages by copying the en-US.lang, and translating the **right** side (right to equals).

---

# Development

We follow a modern, streamlined development workflow centered around a single source of truth.

### The Main Branch
The **main** branch is the default branch for the Electrical Age source repository.
- All active development happens here.
- Pull requests should always be targeted against the **main** branch.
- If you want the "bleeding edge" version of the mod to build yourself, this is the branch to use.

### Releases and Tagging
Instead of maintaining a separate "stable" branch, we use **Git Tags** to mark specific points in time when the code is stable and ready for public use.

- **Stable Releases:** When the community and maintainers agree the `main` branch is in a good state, we create a new release and tag it (e.g., `v1.2.0`).
- **Hotfixes:** If a critical bug is found in a release, we fix it on `main` and create a new minor version tag.

### Contribution Workflow
1. **Fork** the repository and create a local feature branch for your changes.
2. Ensure your code is up to date with the current **main** branch.
3. Submit a **Pull Request** to the **main** branch.
4. Once reviewed and merged, your changes will be part of the next tagged release.

> **Note:** For very large features, please consider breaking them into smaller, logical PRs. This makes it much easier for our team to review and integrate your work quickly.
