# Virtual Physics Developer Tools

This package contains developer tools to enhance the Virtual Physics development experience.

## Overview

The developer tools provide utilities for:
- **Code Generation**: Generate Atoms and Molecules from existing classes
- **Runtime Inspection**: Visualize particle states and relationships
- **Schema Migration**: Convert JSON/XML schemas to Virtual Physics structures
- **IDE Integration**: Templates, syntax highlighting, and debugger support

## Tools

### 1. Code Generator

The Code Generator helps you create Virtual Physics Atoms and Molecules from existing Kotlin classes.

#### AtomGenerator

Generates Atom classes from Kotlin classes:

```kotlin
import symmetrical.tooling.codegen.AtomGenerator

val generator = AtomGenerator()

// Generate an Atom class
val code = generator.generateAtom(MyClass::class, "my.package.atoms")
println(code)

// Generate Atoms for each property
val atoms = generator.generateAtomsFromProperties(MyClass::class)
atoms.forEach { (name, code) ->
    println("// $name")
    println(code)
}

// Get usage examples
val example = generator.generateUsageExample(MyClass::class)
println(example)
```

#### MoleculeGenerator

Generates Molecule classes from Kotlin classes:

```kotlin
import symmetrical.tooling.codegen.MoleculeGenerator

val generator = MoleculeGenerator()

// Generate a Molecule class
val code = generator.generateMolecule(MyClass::class, "my.package.molecules")
println(code)

// Generate a Compound
val compound = generator.generateCompound(MyClass::class)
println(compound)

// Get usage examples
val example = generator.generateUsageExample(MyClass::class)
println(example)
```

### 2. Inspector Tool

The Inspector Tool provides runtime visualization of particle states and relationships.

#### ParticleInspector

Inspect and visualize particles at runtime:

```kotlin
import symmetrical.tooling.inspector.ParticleInspector

val inspector = ParticleInspector()

// Inspect an Atom
val atom = Atom().with("example value")
val state = inspector.inspectAtom(atom)
println("Value: ${state.value}")
println("Protons: ${state.protonCount}")
println("Bindings: ${state.bindings.size}")

// Visualize the state
val visualization = inspector.visualizeParticle(state)
println(visualization)

// Inspect a Molecule
val molecule = MyMolecule()
val moleculeState = inspector.inspectMolecule(molecule)
println("Atoms: ${moleculeState.atomCount}")

// Generate relationship graph (DOT format)
val particles = listOf(state)
val graph = inspector.generateRelationshipGraph(particles)
println(graph) // Can be rendered with Graphviz

// Track state changes
val tracker = inspector.trackStateChanges(atom) { newState ->
    println("Atom changed! New value: ${newState.value}")
}
```

### 3. Migration Tool

The Migration Tool helps convert JSON/XML schemas to Virtual Physics structures.

#### SchemaMigrator

Convert schemas to Virtual Physics:

```kotlin
import symmetrical.tooling.migration.SchemaMigrator

val migrator = SchemaMigrator()

// Create a schema programmatically
val schema = migrator.createSchema(
    "User",
    "name" to "string",
    "email" to "string",
    "age" to "integer",
    "active" to "boolean"
)

// Generate Atoms from schema
val atoms = migrator.generateAtomsFromSchema(schema)
atoms.forEach { (name, code) ->
    println("// $name.kt")
    println(code)
    println()
}

// Generate a Molecule from schema
val molecule = migrator.generateMoleculeFromSchema(schema)
println(molecule)

// Get migration report
val report = migrator.generateMigrationReport(schema)
println(report)

// Generate complete migration package
val package = migrator.generateMigrationPackage(schema, "my.app")
println(package.report)
package.atoms.forEach { (name, code) ->
    // Save to file: $name.kt
}
// Save molecule to file
```

### 4. IDE Utilities

Utilities for IDE plugin development and enhanced editor support.

#### Code Templates

Pre-built templates for common Virtual Physics patterns:

```kotlin
import symmetrical.tooling.ide.CodeTemplates

// Get all templates
val templates = CodeTemplates.getAllTemplates()

// Use specific templates
println(CodeTemplates.ATOM_TEMPLATE)
println(CodeTemplates.MOLECULE_TEMPLATE)
println(CodeTemplates.CONDUCTOR_BINDING_TEMPLATE)
println(CodeTemplates.EMISSION_TEMPLATE)
println(CodeTemplates.ROLLBACK_TEMPLATE)
```

#### Syntax Highlighting

Keywords and categories for syntax highlighting:

```kotlin
import symmetrical.tooling.ide.SyntaxHighlighting

// Get all physics keywords
val keywords = SyntaxHighlighting.PHYSICS_KEYWORDS

// Get keyword categories
val categories = SyntaxHighlighting.getKeywordCategories()
categories.forEach { (category, keywords) ->
    println("$category: $keywords")
}
```

#### Circuit Designer

Visual designer for data binding circuits:

```kotlin
import symmetrical.tooling.ide.*

val designer = CircuitDesigner()

// Create a circuit
val circuit = Circuit(
    nodes = listOf(
        CircuitNode("atom1", "atom", "User Name", 0.0, 0.0),
        CircuitNode("atom2", "atom", "Display Name", 100.0, 0.0)
    ),
    connections = listOf(
        CircuitConnection("atom1", "atom2", "conductor", "both")
    )
)

// Validate the circuit
val validation = designer.validateCircuit(circuit)
if (validation.isValid) {
    println("Circuit is valid!")
} else {
    println("Errors: ${validation.errors}")
}

// Generate code from circuit
val code = designer.generateCodeFromCircuit(circuit)
println(code)
```

#### Debugger Integration

Enhanced debugging support for Virtual Physics:

```kotlin
import symmetrical.tooling.ide.DebuggerIntegration

val debugger = DebuggerIntegration()

// Format for debugger view
val atom = Atom().with("debug value")
val view = debugger.formatAtomForDebugger(atom)
println(view.toTreeString())

// Generate state snapshot
val snapshot = debugger.generateStateSnapshot(atom)
println(snapshot)

// Get watch expressions
val watches = debugger.generateWatchExpressions("myAtom")
watches.forEach { watch ->
    println("${watch.description}: ${watch.expression}")
}

// Quick evaluation expressions
val quickEvals = debugger.getQuickEvaluationExpressions()
quickEvals.forEach { (name, expr) ->
    println("$name: myAtom$expr")
}
```

## Integration with IDEs

### IntelliJ IDEA Plugin

The tools in this package are designed to support IntelliJ IDEA plugin development:

1. **Syntax Highlighting**: Use `SyntaxHighlighting` to configure keyword highlighting
2. **Code Completion**: Use `CodeTemplates` for live templates and code completion
3. **Debugger Views**: Use `DebuggerIntegration` for custom debugger renderers
4. **Visual Designer**: Use `CircuitDesigner` for graphical data binding design

### Example Plugin Integration

```kotlin
// In your IntelliJ plugin

// 1. Register syntax highlighting
class VirtualPhysicsHighlighter : SyntaxHighlighter {
    override fun getHighlightingLexer() = VirtualPhysicsLexer()
    
    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when {
            tokenType.toString() in SyntaxHighlighting.PHYSICS_KEYWORDS -> 
                arrayOf(PHYSICS_KEYWORD)
            else -> emptyArray()
        }
    }
}

// 2. Add code completion
class VirtualPhysicsCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    CodeTemplates.getAllTemplates().forEach { (name, template) ->
                        result.addElement(
                            LookupElementBuilder.create(name)
                                .withInsertHandler { context, item ->
                                    // Insert template
                                }
                        )
                    }
                }
            }
        )
    }
}

// 3. Custom debugger renderer
class AtomRenderer : NodeRenderer() {
    override fun calcLabel(
        descriptor: ValueDescriptor,
        evaluationContext: EvaluationContext,
        listener: DescriptorLabelListener
    ): String {
        val debugger = DebuggerIntegration()
        val atom = descriptor.value as? Atom
        return atom?.let { debugger.formatAtomForDebugger(it).value } ?: "Unknown"
    }
}
```

## Best Practices

1. **Code Generation**: Always review generated code before committing
2. **Inspection**: Use the inspector in development/debug mode only
3. **Migration**: Test migrated schemas with sample data
4. **Templates**: Customize templates for your project's coding standards

## Examples

See the `examples/` directory for complete examples of using these tools.

## API Reference

Full API documentation is available in the KDoc comments of each class.
