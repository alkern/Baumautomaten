package parser

import org.junit.Test
import org.junit.Assert.assertEquals

class TreeParserTest {

    @Test
    fun testFormatting() {
        val parser = TreeParser();
        assertEquals("(a)", parser.formatInputTree("( (a) )"))
    }
}