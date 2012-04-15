package com.siniatech.dokz.layout;

import static junit.framework.Assert.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Arrays;

import javax.swing.JPanel;

import org.junit.Test;

public class TestRemovingLayouter {

    private RemovingLayouter layouter = new RemovingLayouter();

    @Test
    public void shouldntBarfWithEmptyList() {
        layouter.doLayout( Arrays.<Component> asList(), new Dimension( 200, 200 ) );
    }

    @Test
    public void shouldFillEntireWithOneCompH() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1 ), new Dimension( 100, 50 ) );
        assertEquals( new Rectangle( 0, 0, 100, 50 ), c1.getBounds() );
    }

    @Test
    public void shouldFillEntireWithOneCompV() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1 ), new Dimension( 50, 100 ) );
        assertEquals( new Rectangle( 0, 0, 50, 100 ), c1.getBounds() );
    }

}
