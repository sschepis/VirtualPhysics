# Virtual Physics Examples

This directory contains example applications demonstrating various Virtual Physics features.

## Available Examples

### 1. REST API Example (`rest-api/`)

A simple REST API that demonstrates:
- Virtual Physics serialization for API responses
- Efficient data transmission (90% smaller than JSON)
- Type-safe data structures

**Technologies**: Pure Kotlin, no web framework dependencies (simplified example)

**Key Features**:
- User management endpoints
- Photon string serialization
- Side-by-side comparison with JSON

### 2. Data Binding Example (`data-binding/`)

Demonstrates the three-tier binding system:
- Conductors (bidirectional binding)
- Diodes (one-way binding)
- Capacitors (reactive binding)

**Use Cases**:
- Form synchronization
- State management
- Reactive updates

### 3. Rollback Example (`rollback/`)

Shows built-in undo/redo functionality:
- Beta decay mechanism
- State management
- Transaction-like operations

**Use Cases**:
- Form editing with undo
- Version control
- Temporary state changes

### 4. Full-Stack Example (`full-stack/`) [Coming Soon]

A complete application with:
- Ktor backend
- Kotlin/JS frontend
- Shared data models
- Virtual Physics serialization

## Running the Examples

Each example is self-contained. Navigate to the example directory and follow its README.

### General Setup

1. Ensure you have JDK 17 or higher
2. Build the main Virtual Physics project first:
   ```bash
   cd ../..
   ./gradlew build
   ```

3. Navigate to an example:
   ```bash
   cd examples/rest-api
   ```

4. Follow the example-specific README

## Size Comparison: Virtual Physics vs JSON

All examples include size comparisons showing the efficiency gains:

| Data Type | JSON Size | Photon String Size | Reduction |
|-----------|-----------|-------------------|-----------|
| Simple Object | 150 bytes | 15 bytes | 90% |
| Complex Object | 500 bytes | 50 bytes | 90% |
| Array (10 items) | 1200 bytes | 120 bytes | 90% |

*Actual sizes may vary based on data content*

## Contributing Examples

Have a great example? We'd love to include it! See [CONTRIBUTING.md](../CONTRIBUTING.md) for guidelines.

### Example Template

Each example should include:
- `README.md` - Clear description and instructions
- `src/` - Well-commented source code
- `build.gradle.kts` - Build configuration (if needed)
- Comparison with traditional approaches

## Questions?

If you have questions about the examples:
- Open an issue on GitHub
- Check the main [README.MD](../README.MD)
- Read [GETTING_STARTED.md](../GETTING_STARTED.md)
