package symmetrical.tooling

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

import symmetrical.tooling.codegen.AtomGenerator
import symmetrical.tooling.codegen.MoleculeGenerator
import symmetrical.tooling.inspector.ParticleInspector
import symmetrical.tooling.migration.SchemaMigrator
import symmetrical.tooling.ide.*
import symmetrical.physics.atomic.atoms.Atom

/**
 * Simple test to verify developer tools functionality
 */
class DeveloperToolsTest {
    
    data class TestClass(val field1: String, val field2: Int)
    
    fun testAtomGenerator() {
        println("Testing AtomGenerator...")
        val generator = AtomGenerator()
        val code = generator.generateAtom(TestClass::class, "test.package")
        
        assert(code.contains("class TestClassAtom")) { "Generated code should contain class name" }
        assert(code.contains("package test.package")) { "Generated code should contain package" }
        assert(code.contains("Hydrogen")) { "Generated code should extend Hydrogen" }
        
        println("✓ AtomGenerator test passed")
    }
    
    fun testMoleculeGenerator() {
        println("Testing MoleculeGenerator...")
        val generator = MoleculeGenerator()
        val code = generator.generateMolecule(TestClass::class, "test.package")
        
        assert(code.contains("class TestClassMolecule")) { "Generated code should contain class name" }
        assert(code.contains("package test.package")) { "Generated code should contain package" }
        assert(code.contains("field1Atom")) { "Generated code should have field1 atom" }
        assert(code.contains("field2Atom")) { "Generated code should have field2 atom" }
        
        println("✓ MoleculeGenerator test passed")
    }
    
    fun testParticleInspector() {
        println("Testing ParticleInspector...")
        val inspector = ParticleInspector()
        val atom = Atom().with("test value")
        val state = inspector.inspectAtom(atom)
        
        assert(state.value == "test value") { "State should contain correct value" }
        assert(state.type == "Atom") { "State type should be Atom" }
        assert(state.protonCount >= 0) { "Proton count should be non-negative" }
        
        val visualization = inspector.visualizeParticle(state)
        assert(visualization.contains("test value")) { "Visualization should contain value" }
        
        println("✓ ParticleInspector test passed")
    }
    
    fun testSchemaMigrator() {
        println("Testing SchemaMigrator...")
        val migrator = SchemaMigrator()
        val schema = migrator.createSchema(
            "TestSchema",
            "field1" to "string",
            "field2" to "integer"
        )
        
        assert(schema.name == "TestSchema") { "Schema should have correct name" }
        assert(schema.fields.size == 2) { "Schema should have 2 fields" }
        
        val atoms = migrator.generateAtomsFromSchema(schema, "test.atoms")
        assert(atoms.size == 2) { "Should generate 2 atoms" }
        
        val molecule = migrator.generateMoleculeFromSchema(schema, "test.molecules")
        assert(molecule.contains("TestSchemaMolecule")) { "Molecule should have correct name" }
        
        println("✓ SchemaMigrator test passed")
    }
    
    fun testCodeTemplates() {
        println("Testing CodeTemplates...")
        val templates = CodeTemplates.getAllTemplates()
        
        assert(templates.containsKey("atom")) { "Should have atom template" }
        assert(templates.containsKey("molecule")) { "Should have molecule template" }
        assert(templates.containsKey("conductor")) { "Should have conductor template" }
        assert(templates.containsKey("emission")) { "Should have emission template" }
        
        println("✓ CodeTemplates test passed")
    }
    
    fun testSyntaxHighlighting() {
        println("Testing SyntaxHighlighting...")
        val keywords = SyntaxHighlighting.PHYSICS_KEYWORDS
        
        assert(keywords.contains("Atom")) { "Should contain Atom keyword" }
        assert(keywords.contains("Molecule")) { "Should contain Molecule keyword" }
        assert(keywords.contains("conductor")) { "Should contain conductor keyword" }
        assert(keywords.contains("emit")) { "Should contain emit keyword" }
        
        val categories = SyntaxHighlighting.getKeywordCategories()
        assert(categories.isNotEmpty()) { "Should have keyword categories" }
        
        println("✓ SyntaxHighlighting test passed")
    }
    
    fun testCircuitDesigner() {
        println("Testing CircuitDesigner...")
        val designer = CircuitDesigner()
        
        val circuit = Circuit(
            nodes = listOf(
                CircuitNode("node1", "atom", "Test1", 0.0, 0.0),
                CircuitNode("node2", "atom", "Test2", 100.0, 0.0)
            ),
            connections = listOf(
                CircuitConnection("node1", "node2", "conductor", "both")
            )
        )
        
        val validation = designer.validateCircuit(circuit)
        assert(validation.isValid) { "Valid circuit should pass validation" }
        
        val code = designer.generateCodeFromCircuit(circuit)
        assert(code.contains("node1")) { "Generated code should contain node1" }
        assert(code.contains("conductor")) { "Generated code should contain conductor" }
        
        println("✓ CircuitDesigner test passed")
    }
    
    fun testDebuggerIntegration() {
        println("Testing DebuggerIntegration...")
        val debugger = DebuggerIntegration()
        val atom = Atom().with("debug test")
        
        val view = debugger.formatAtomForDebugger(atom)
        assert(view.value.contains("debug test")) { "View should contain atom value" }
        assert(view.type == "Atom") { "View type should be Atom" }
        
        val snapshot = debugger.generateStateSnapshot(atom)
        assert(snapshot.contains("debug test")) { "Snapshot should contain value" }
        assert(snapshot.contains("ATOM STATE SNAPSHOT")) { "Snapshot should have header" }
        
        val watches = debugger.generateWatchExpressions("testAtom")
        assert(watches.isNotEmpty()) { "Should have watch expressions" }
        
        println("✓ DebuggerIntegration test passed")
    }
    
    fun runAllTests() {
        println("═══════════════════════════════════════════")
        println("  Running Developer Tools Tests")
        println("═══════════════════════════════════════════\n")
        
        testAtomGenerator()
        testMoleculeGenerator()
        testParticleInspector()
        testSchemaMigrator()
        testCodeTemplates()
        testSyntaxHighlighting()
        testCircuitDesigner()
        testDebuggerIntegration()
        
        println("\n═══════════════════════════════════════════")
        println("  All Tests Passed! ✓")
        println("═══════════════════════════════════════════")
    }
}

fun main() {
    val test = DeveloperToolsTest()
    test.runAllTests()
}
