import org.junit.Assert.assertEquals
import org.junit.Test
import parser.RecursiveLineParser
import parser.Tokenizer

class BugTest {

    @Test
    fun testDoubledLeafInOneTreeAppearsTwoTimesInRegularRules() {
        val parser = RecursiveLineParser()
        val input = "( (S (S-ADV (VP (VBN Given) " +
                "(NP (DT that) (NN choice)))) " +
                "(, ,) (NP-SBJ (NP (NP (NNS associates)) " +
                "(PP (IN of) (NP (NNP Mr.) (NNP Hahn)))) " +
                "(CC and) (NP (NN industry) (NNS observers))) " +
                "(VP (VBP say) (SBAR (S (NP-SBJ-2 (NP (DT the) " +
                "(JJ former) (NN university) (NN president)) " +
                "(: --) (SBAR (WHNP-1 (WP who)) " +
                "(S (VP (VBZ has) (VP (VBN developed) " +
                "(NP (NP (DT a) (NN reputation)) " +
                "(PP (IN for) (S-NOM (RB not) " +
                "(VP (VBG overpaying) (PP-CLR (IN for) " +
                "(NP (NN anything))))))))))) (: --)) " +
                "(VP (MD would) (VP (VB fold)))))) (. .)) )" //Tree Number 9 from examples
        val tokenizer = Tokenizer()
        val tree = parser.parseLinesToTree(tokenizer.scan(input))

        val regularRules = tree.regularProductionsForWholeTree
        assertEquals(60, regularRules.size)
    }
}
