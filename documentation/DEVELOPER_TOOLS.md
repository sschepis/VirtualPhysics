# Virtual Physics Developer Tools

Complete suite of developer tools for enhancing the Virtual Physics development experience. These tools help you migrate to Virtual Physics, generate code, inspect runtime state, and integrate with IDEs.

## Quick Start

```kotlin
// 1. Generate Atoms from existing classes
val atomGenerator = AtomGenerator()
val code = atomGenerator.generateAtom(MyClass::class)
println(code)

// 2. Inspect runtime particle state
val inspector = ParticleInspector()
val atom = Atom().with("value")
val state = inspector.inspectAtom(atom)
println(inspector.visualizeParticle(state))

// 3. Migrate from JSON/XML schemas
val migrator = SchemaMigrator()
val schema = migrator.createSchema("User", "name" to "string", "email" to "string")
val molecule = migrator.generateMoleculeFromSchema(schema)
println(molecule)
```

## Available Tools

### 📝 Code Generators

Generate Virtual Physics structures from existing code:

- **AtomGenerator** - Create Atoms from Kotlin classes
- **MoleculeGenerator** - Create Molecules and Compounds from data structures
- Automatic usage example generation
- Support for multiple properties per class

**Example:**
```kotlin
import symmetrical.tooling.codegen.AtomGenerator

val generator = AtomGenerator()
val code = generator.generateAtom(User::class, "my.package.atoms")
// Generates a complete Atom class ready to use
```

### 🔍 Inspector Tool

Runtime visualization and monitoring:

- **ParticleInspector** - Inspect Atoms, Molecules, and Compounds
- Visual particle state representation
- DOT format graph generation for Graphviz
- State change tracking
- Relationship mapping

**Example:**
```kotlin
import symmetrical.tooling.inspector.ParticleInspector

val inspector = ParticleInspector()
val atom = Atom().with("data")
val state = inspector.inspectAtom(atom)

// Get visual representation
println(inspector.visualizeParticle(state))

// Generate relationship graph
val graph = inspector.generateRelationshipGraph(listOf(state))
```

### 🔄 Migration Tool

Convert existing schemas to Virtual Physics:

- **SchemaMigrator** - Automated schema conversion
- JSON/XML schema support
- Migration report generation
- Batch conversion capabilities
- Type mapping from common formats

**Example:**
```kotlin
import symmetrical.tooling.migration.SchemaMigrator

val migrator = SchemaMigrator()
val schema = migrator.createSchema(
    "Address",
    "street" to "string",
    "city" to "string",
    "zipCode" to "string"
)

// Generate complete migration package
val package = migrator.generateMigrationPackage(schema)
println(package.report)
```

### 💻 IDE Utilities

IntelliJ IDEA plugin foundation:

- **CodeTemplates** - Live templates for common patterns
- **SyntaxHighlighting** - Physics keyword definitions
- **CircuitDesigner** - Visual data binding designer
- **DebuggerIntegration** - Enhanced debugging views

**Example:**
```kotlin
import symmetrical.tooling.ide.*

// Use code templates
val template = CodeTemplates.ATOM_TEMPLATE

// Get syntax highlighting keywords
val keywords = SyntaxHighlighting.PHYSICS_KEYWORDS

// Design circuits visually
val designer = CircuitDesigner()
val circuit = Circuit(/* ... */)
val code = designer.generateCodeFromCircuit(circuit)

// Enhanced debugging
val debugger = DebuggerIntegration()
val view = debugger.formatAtomForDebugger(myAtom)
```

## Running Examples

The project includes comprehensive examples demonstrating all tools:

```bash
# Build the project
./gradlew build

# Run examples
kotlin -cp build/classes/kotlin/main symmetrical.tooling.examples.DeveloperToolsExamplesKt
```

Or programmatically:

```kotlin
import symmetrical.tooling.examples.DeveloperToolsExamples

val examples = DeveloperToolsExamples()
examples.codeGenerationExample()
examples.inspectionExample()
examples.migrationExample()
examples.templateExample()
examples.syntaxHighlightingExample()
examples.circuitDesignerExample()
examples.debuggerIntegrationExample()
```

## Use Cases

### 1. Migrating Existing Code

Convert your traditional data classes to Virtual Physics:

```kotlin
// Before
data class User(val name: String, val email: String)

// Generate Virtual Physics version
val generator = MoleculeGenerator()
val code = generator.generateMolecule(User::class)
// Creates UserMolecule with all fields as Atoms
```

### 2. Debugging Complex Bindings

Visualize data binding relationships:

```kotlin
val atom1 = Atom().with("value1")
val atom2 = Atom().with("value2")
atom1.conductor(atom2)

val inspector = ParticleInspector()
val state = inspector.inspectAtom(atom1)
println(inspector.visualizeParticle(state))
// Shows bindings, state, and metadata
```

### 3. Schema-Driven Development

Generate code from schemas:

```kotlin
val migrator = SchemaMigrator()
val schema = migrator.parseJsonSchema(jsonSchemaString)
val atoms = migrator.generateAtomsFromSchema(schema)
val molecule = migrator.generateMoleculeFromSchema(schema)
// Auto-generates all necessary Virtual Physics structures
```

### 4. IDE Integration

Build IDE plugins using the utilities:

```kotlin
// In your IntelliJ plugin
class VirtualPhysicsCompletionContributor : CompletionContributor() {
    init {
        // Use CodeTemplates for auto-completion
        CodeTemplates.getAllTemplates().forEach { (name, template) ->
            // Add to completion suggestions
        }
    }
}
```

## API Documentation

### Code Generators

**AtomGenerator**
- `generateAtom(kClass, packageName)` - Generate single Atom
- `generateAtomsFromProperties(kClass, packageName)` - Generate Atom per property
- `generateUsageExample(kClass)` - Create usage examples

**MoleculeGenerator**
- `generateMolecule(kClass, packageName)` - Generate Molecule
- `generateCompound(kClass, packageName)` - Generate Compound
- `generateUsageExample(kClass)` - Create usage examples

### Inspector Tool

**ParticleInspector**
- `inspectAtom(atom)` - Get atom state
- `inspectMolecule(molecule)` - Get molecule state
- `inspectCompound(compound)` - Get compound state
- `visualizeParticle(state)` - Create text visualization
- `generateRelationshipGraph(particles)` - Create DOT graph
- `trackStateChanges(atom, callback)` - Monitor changes

### Migration Tool

**SchemaMigrator**
- `createSchema(name, fields...)` - Define schema
- `parseJsonSchema(json)` - Parse JSON schema (placeholder)
- `parseXmlSchema(xml)` - Parse XML schema (placeholder)
- `generateAtomsFromSchema(schema)` - Create Atoms
- `generateMoleculeFromSchema(schema)` - Create Molecule
- `generateMigrationReport(schema)` - Get migration analysis
- `generateMigrationPackage(schema)` - Complete package

### IDE Utilities

**CodeTemplates**
- `ATOM_TEMPLATE` - Atom class template
- `MOLECULE_TEMPLATE` - Molecule class template
- `CONDUCTOR_BINDING_TEMPLATE` - Two-way binding
- `DIODE_BINDING_TEMPLATE` - One-way binding
- `EMISSION_TEMPLATE` - Serialization
- `ROLLBACK_TEMPLATE` - Beta decay
- `getAllTemplates()` - Get all templates

**SyntaxHighlighting**
- `PHYSICS_KEYWORDS` - All physics keywords
- `SPECIAL_METHODS` - Special method names
- `getKeywordCategories()` - Categorized keywords

**CircuitDesigner**
- `validateCircuit(circuit)` - Validate circuit
- `generateCodeFromCircuit(circuit)` - Generate code

**DebuggerIntegration**
- `formatAtomForDebugger(atom)` - Format for debugger
- `formatMoleculeForDebugger(molecule)` - Format molecule
- `generateStateSnapshot(atom)` - Create snapshot
- `generateWatchExpressions(varName)` - Watch expressions
- `getQuickEvaluationExpressions()` - Quick evals

## Architecture

```
symmetrical.tooling/
├── codegen/
│   ├── AtomGenerator.kt       - Atom generation
│   └── MoleculeGenerator.kt   - Molecule/Compound generation
├── inspector/
│   └── ParticleInspector.kt   - Runtime inspection
├── migration/
│   └── SchemaMigrator.kt      - Schema conversion
├── ide/
│   ├── IDEUtilities.kt        - Templates & syntax
│   └── DebuggerIntegration.kt - Debug support
├── examples/
│   └── DeveloperToolsExamples.kt - Usage examples
└── README.md                   - This file
```

## Contributing

To add new tools:

1. Create tool in appropriate package
2. Follow existing naming conventions
3. Add comprehensive KDoc comments
4. Include usage examples
5. Add to DeveloperToolsExamples
6. Update this README

## Future Enhancements

Planned features:

- Full JSON/XML schema parser integration
- IntelliJ IDEA plugin implementation
- Visual circuit designer UI
- Real-time particle monitoring dashboard
- Code refactoring tools
- Performance profiler integration
- Multi-language code generation
- Schema evolution tools

## License

Part of Virtual Physics - LGPL v3.0

Copyright (C) [2024] Stephen R. DeSofi AKA Eschelon Azha
