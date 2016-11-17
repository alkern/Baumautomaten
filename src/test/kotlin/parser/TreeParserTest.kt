package parser

import org.junit.Assert.assertEquals
import org.junit.Test

class TreeParserTest {

    @Test
    fun testFormatting() {
        val parser = TreeParser()
        assertEquals("(a)", parser.formatInputTree("( (a) )"))
    }
}