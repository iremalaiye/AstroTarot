package com.example.astrotarot;

import static org.junit.Assert.assertEquals;


import com.example.astrotarot.tarotFragment.Tarot;
import com.example.astrotarot.tarotFragment.TarotFragmentCareer;

import org.junit.Test;

public class TarotUnitTest {

    @Test
    public void isTarotCreated(){
        Tarot tarot = new Tarot(1,"description","title");
        assertEquals(1,tarot.getId());
        assertEquals("description",tarot.getDescription());
        assertEquals("title",tarot.getTitle());
    }
}
