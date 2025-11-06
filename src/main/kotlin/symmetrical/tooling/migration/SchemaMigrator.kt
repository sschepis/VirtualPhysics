package symmetrical.tooling.migration

/*
 * This file is part of Virtual Physics.
 *
 * Copyright (C) [2024] Stephen R. DeSofi AKA Eschelon Azha
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Represents a schema field that can be converted to Virtual Physics structures.
 */
data class SchemaField(
    val name: String,
    val type: String,
    val isRequired: Boolean = false,
    val isArray: Boolean = false,
    val nested: List<SchemaField>? = null,
    val description: String? = null
)

/**
 * Represents a complete schema that can be converted to Virtual Physics.
 */
data class Schema(
    val name: String,
    val fields: List<SchemaField>,
    val description: String? = null
)

/**
 * Migration tool for converting JSON/XML schemas to Virtual Physics structures.
 * Analyzes schema definitions and generates corresponding Atoms, Molecules, and Compounds.
 */
class SchemaMigrator {
    
    /**
     * Analyzes a JSON schema string and extracts field information.
     * This is a simplified parser for demonstration purposes.
     */
    fun parseJsonSchema(schemaJson: String): Schema {
        // Simplified parsing - in production, use a proper JSON schema parser
        val fields = mutableListOf<SchemaField>()
        
        // This would parse actual JSON schema format
        // For now, returning a placeholder that shows the expected structure
        return Schema(
            name = "ParsedSchema",
            fields = fields,
            description = "Schema parsed from JSON"
        )
    }
    
    /**
     * Analyzes an XML schema string and extracts field information.
     */
    fun parseXmlSchema(schemaXml: String): Schema {
        // Simplified parsing - in production, use a proper XML schema parser
        val fields = mutableListOf<SchemaField>()
        
        return Schema(
            name = "ParsedSchema",
            fields = fields,
            description = "Schema parsed from XML"
        )
    }
    
    /**
     * Creates a schema from a simple field definition.
     * Useful for programmatic schema creation.
     */
    fun createSchema(name: String, vararg fields: Pair<String, String>): Schema {
        val schemaFields = fields.map { (fieldName, fieldType) ->
            SchemaField(
                name = fieldName,
                type = fieldType,
                isRequired = true
            )
        }
        
        return Schema(
            name = name,
            fields = schemaFields
        )
    }
    
    /**
     * Generates Virtual Physics Atom classes from a schema.
     * Creates one Atom class for each primitive field.
     */
    fun generateAtomsFromSchema(schema: Schema, packageName: String = "symmetrical.dictionary.atoms"): Map<String, String> {
        val result = mutableMapOf<String, String>()
        
        schema.fields.forEach { field ->
            if (field.nested == null && !field.isArray) {
                val atomName = "${field.name.replaceFirstChar { it.uppercase() }}Atom"
                val code = buildString {
                    append("package $packageName\n\n")
                    append("import symmetrical.physics.atomic.atoms.elements.Hydrogen\n")
                    append("import symmetrical.physics.subatomic.luminescent.IMatterAntiMatter\n")
                    append("import symmetrical.physics.subatomic.luminescent.MatterAntiMatter\n\n")
                    
                    if (field.description != null) {
                        append("/**\n")
                        append(" * ${field.description}\n")
                        append(" * Generated from schema: ${schema.name}\n")
                        append(" */\n")
                    }
                    
                    append("class $atomName(\n")
                    append("    private val matterAntiMatter: IMatterAntiMatter = MatterAntiMatter().with($atomName::class)\n")
                    append(") : Hydrogen(), IMatterAntiMatter by matterAntiMatter {\n\n")
                    append("    fun with(value: ${mapSchemaTypeToKotlin(field.type)}?): $atomName {\n")
                    append("        setAtomicValue(value, true)\n")
                    append("        return this\n")
                    append("    }\n")
                    append("}\n")
                }
                
                result[atomName] = code
            }
        }
        
        return result
    }
    
    /**
     * Generates a Virtual Physics Molecule class from a schema.
     */
    fun generateMoleculeFromSchema(schema: Schema, packageName: String = "symmetrical.dictionary.molecules"): String {
        val moleculeName = "${schema.name}Molecule"
        
        return buildString {
            append("package $packageName\n\n")
            append("import symmetrical.physics.atomic.substance.molecules.Molecule\n")
            append("import symmetrical.physics.atomic.atoms.Atom\n")
            append("import symmetrical.physics.subatomic.luminescent.IMatterAntiMatter\n")
            append("import symmetrical.physics.subatomic.luminescent.MatterAntiMatter\n\n")
            
            if (schema.description != null) {
                append("/**\n")
                append(" * ${schema.description}\n")
                append(" * Generated from schema: ${schema.name}\n")
                append(" * Contains ${schema.fields.size} field(s)\n")
                append(" */\n")
            }
            
            append("class $moleculeName(\n")
            append("    private val matterAntiMatter: IMatterAntiMatter = MatterAntiMatter().with($moleculeName::class)\n")
            append(") : Molecule(), IMatterAntiMatter by matterAntiMatter {\n\n")
            
            // Atom properties
            schema.fields.forEach { field ->
                append("    private var ${field.name}Atom: Atom = Atom()\n")
            }
            append("\n")
            
            // Constructor
            append("    fun with(")
            schema.fields.forEachIndexed { index, field ->
                if (index > 0) append(", ")
                append("${field.name}: ${mapSchemaTypeToKotlin(field.type)}?")
            }
            append("): $moleculeName {\n")
            
            schema.fields.forEach { field ->
                append("        ${field.name}Atom.with(${field.name})\n")
                append("        bind(${field.name}Atom)\n")
            }
            
            append("        return this\n")
            append("    }\n\n")
            
            // Getters and setters
            schema.fields.forEach { field ->
                val capitalizedName = field.name.replaceFirstChar { it.uppercase() }
                val kotlinType = mapSchemaTypeToKotlin(field.type)
                
                append("    fun get$capitalizedName(): Atom = ${field.name}Atom\n")
                append("    fun get${capitalizedName}Value(): $kotlinType? = ${field.name}Atom.getContent() as? $kotlinType\n")
                append("    fun set$capitalizedName(value: $kotlinType?): $moleculeName {\n")
                append("        ${field.name}Atom.with(value)\n")
                append("        return this\n")
                append("    }\n\n")
            }
            
            append("}\n")
        }
    }
    
    /**
     * Generates a complete migration report with recommendations.
     */
    fun generateMigrationReport(schema: Schema): String {
        return buildString {
            append("╔═══════════════════════════════════════════════════════════╗\n")
            append("║ Virtual Physics Migration Report                          ║\n")
            append("╠═══════════════════════════════════════════════════════════╣\n")
            append("║ Schema: ${schema.name.padEnd(48)} ║\n")
            append("║ Fields: ${schema.fields.size.toString().padEnd(48)} ║\n")
            append("╠═══════════════════════════════════════════════════════════╣\n")
            
            val primitiveFields = schema.fields.count { it.nested == null && !it.isArray }
            val complexFields = schema.fields.count { it.nested != null || it.isArray }
            
            append("║ Primitive fields (→ Atoms): ${primitiveFields.toString().padEnd(31)} ║\n")
            append("║ Complex fields (→ Molecules): ${complexFields.toString().padEnd(29)} ║\n")
            append("╠═══════════════════════════════════════════════════════════╣\n")
            append("║ Recommendations:                                          ║\n")
            append("║                                                           ║\n")
            
            if (primitiveFields > 0) {
                append("║ 1. Create individual Atoms for primitive fields          ║\n")
            }
            if (schema.fields.size > 3) {
                append("║ 2. Create a Molecule to combine all fields               ║\n")
            }
            if (complexFields > 0) {
                append("║ 3. Create nested Molecules for complex structures        ║\n")
            }
            append("║ 4. Use conductors for two-way data binding                ║\n")
            append("║ 5. Implement emit() for efficient serialization           ║\n")
            
            append("╚═══════════════════════════════════════════════════════════╝\n")
            
            append("\nField Mapping:\n")
            schema.fields.forEach { field ->
                val arrow = if (field.nested != null || field.isArray) "→ Molecule" else "→ Atom"
                append("  • ${field.name} (${field.type}) $arrow\n")
            }
        }
    }
    
    /**
     * Maps schema type strings to Kotlin types.
     */
    private fun mapSchemaTypeToKotlin(schemaType: String): String {
        return when (schemaType.lowercase()) {
            "string", "text" -> "String"
            "integer", "int" -> "Int"
            "number", "double", "float" -> "Double"
            "boolean", "bool" -> "Boolean"
            "array", "list" -> "List<Any>"
            "object" -> "Map<String, Any>"
            else -> "Any"
        }
    }
    
    /**
     * Generates a complete migration package with all necessary files.
     */
    fun generateMigrationPackage(schema: Schema, basePackage: String = "symmetrical.dictionary"): MigrationPackage {
        val atoms = generateAtomsFromSchema(schema, "$basePackage.atoms")
        val molecule = generateMoleculeFromSchema(schema, "$basePackage.molecules")
        val report = generateMigrationReport(schema)
        
        return MigrationPackage(
            schemaName = schema.name,
            atoms = atoms,
            molecule = molecule,
            report = report
        )
    }
}

/**
 * Container for all generated migration artifacts.
 */
data class MigrationPackage(
    val schemaName: String,
    val atoms: Map<String, String>,
    val molecule: String,
    val report: String
)
