
package org.onebeartoe.deep.learning.nlp.language.pos;

/**
 * This class enumerates the the parts of speech as defined in this Penn State
 * University document:
 * 
 *      https://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html
 */
public enum PartsOfSpeech
{
    // Nouns    
    NN("Noun, singular or mass"),
    NNS("Noun, plural"),
    NNP("Proper noun, singular"),
    NNPS("Proper noun, plural"),
        
    // Pronouns
    PRP("Personal pronoun"),
    PRP$("Possessive pronoun"),
    WP("Wh-pronoun"),
    WP$("Possessive wh-pronoun"),
        
    // Adjectives
    JJ("Adjective"),
    JJR("Adjective, comparative"),
    JJS("Adjective, superlative");

    public final String description;

    PartsOfSpeech(String description)
    {
        this.description = description;
    }            
}
