# Contributing to Virtual Physics

Thank you for your interest in contributing to Virtual Physics! This document provides guidelines and instructions for contributing to the project.

## Table of Contents

- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [How to Contribute](#how-to-contribute)
- [Code Style Guidelines](#code-style-guidelines)
- [Testing Guidelines](#testing-guidelines)
- [Pull Request Process](#pull-request-process)
- [Community](#community)

## Getting Started

Virtual Physics is a revolutionary physics-inspired framework for building robust, reliable software systems in Kotlin. Before contributing, please:

1. Read the [README.MD](README.MD) to understand the project's vision and features
2. Review the documentation in the `documentation/` directory
3. Familiarize yourself with the physics concepts that inspire the framework

## Development Setup

### Prerequisites

- JDK 17 or higher
- IntelliJ IDEA (recommended) or any Kotlin-compatible IDE
- Git

### Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/sschepis/VirtualPhysics.git
   cd VirtualPhysics
   ```

2. **Build the project:**
   ```bash
   ./gradlew build
   ```

3. **Run tests:**
   ```bash
   ./gradlew test
   ```

4. **Open in IntelliJ IDEA:**
   - Open IntelliJ IDEA
   - Select "Open" and choose the VirtualPhysics directory
   - IntelliJ will automatically detect the Gradle project and configure it

## How to Contribute

### Reporting Bugs

If you find a bug, please create an issue with:
- A clear, descriptive title
- Detailed steps to reproduce the issue
- Expected vs actual behavior
- Your environment (OS, JDK version, etc.)
- Code samples or test cases if applicable

### Suggesting Enhancements

We welcome enhancement suggestions! Please create an issue with:
- A clear description of the enhancement
- Why this enhancement would be useful
- Possible implementation approaches
- Examples of how it would be used

### Contributing Code

1. **Find or create an issue** describing what you want to work on
2. **Fork the repository** and create a new branch from `main`
3. **Make your changes** following the code style guidelines
4. **Write tests** for your changes
5. **Ensure all tests pass** with `./gradlew test`
6. **Submit a pull request** with a clear description of your changes

## Code Style Guidelines

### Kotlin Style

- Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Keep functions small and focused on a single responsibility
- Add KDoc comments for public APIs

### Physics Metaphors

When adding new features:
- Maintain consistency with the physics-inspired paradigm
- Use appropriate physics concepts (particles, forces, reactions, etc.)
- Include Wikipedia links in class documentation when relevant
- Ensure the metaphor makes intuitive sense

### Code Organization

- Keep related classes in the same package
- Follow the existing package structure
- Use appropriate visibility modifiers (public, internal, private)

## Testing Guidelines

### Writing Tests

- Write tests for all new functionality
- Use descriptive test names that explain what is being tested
- Follow the existing test structure in `src/test/kotlin/`
- Test edge cases and error conditions
- Aim for high test coverage

### Test Structure

```kotlin
class VTestMyFeature {
    fun testFeatureBehavior() {
        // Arrange: Set up test data
        val atom = Atom().with("TestValue")
        
        // Act: Execute the functionality
        val result = atom.someOperation()
        
        // Assert: Verify the results
        assert(result == expectedValue)
    }
}
```

### Running Tests

Run all tests:
```bash
./gradlew test
```

Run specific test:
```bash
./gradlew test --tests "VTestMyFeature.testFeatureBehavior"
```

## Pull Request Process

1. **Update documentation** if you're adding or changing functionality
2. **Update the README.MD** if adding new features
3. **Ensure all tests pass** and there are no build errors
4. **Write a clear PR description** explaining:
   - What changes you made
   - Why you made them
   - How to test them
5. **Link related issues** in your PR description
6. **Respond to code review feedback** promptly
7. **Squash commits** if requested before merging

### PR Title Format

Use descriptive PR titles:
- `Add: New feature description`
- `Fix: Bug description`
- `Improve: Enhancement description`
- `Docs: Documentation update description`
- `Test: Test improvement description`

## Community

### Code of Conduct

We are committed to providing a welcoming and inclusive environment. Please:
- Be respectful and constructive in all interactions
- Welcome newcomers and help them get started
- Focus on what is best for the community
- Show empathy towards others

### Getting Help

- Create an issue for questions
- Join discussions in the project forum
- Check existing documentation and examples

### Recognition

Contributors will be recognized in:
- The project's contributors list
- Release notes for significant contributions
- Special recognition for major features or improvements

## Development Priorities

Currently, the project focuses on:

1. **Documentation** - Improving guides, tutorials, and API documentation
2. **Examples** - Creating real-world example applications
3. **Performance** - Benchmarking and optimization
4. **Testing** - Increasing test coverage
5. **Tooling** - IDE plugins and developer tools

Check the [README.MD](README.MD) for the full roadmap and suggested enhancements.

## Questions?

If you have questions about contributing, feel free to:
- Open an issue with the "question" label
- Start a discussion in the project forum
- Reach out to the maintainers

Thank you for contributing to Virtual Physics! Together, we're building the future of physics-inspired software engineering.
