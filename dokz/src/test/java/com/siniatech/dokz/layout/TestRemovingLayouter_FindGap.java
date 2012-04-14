package com.siniatech.dokz.layout;

import static junit.framework.Assert.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Arrays;

import javax.swing.JPanel;

import org.junit.Test;

public class TestRemovingLayouter_FindGap {

    private RemovingLayouter layouter = new RemovingLayouter();

    @Test
    public void shouldFindOneBigGapInEmptyContainer() {
        Rectangle gap = layouter.findGap( Arrays.<Component> asList(), new Dimension( 200, 200 ), 0, 0 );
        assertEquals( new Rectangle( 0, 0, 200, 200 ), gap );
    }

    @Test
    public void shouldFindNoGapWithOneBigComponent() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Rectangle gap = layouter.findGap( Arrays.asList( c1 ), new Dimension( 50, 50 ), 0, 0 );
        assertEquals( new Rectangle( 0, 0, 0, 0 ), gap );
    }

    @Test
    public void shouldFindSimpleGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Rectangle gap = layouter.findGap( Arrays.asList( c1 ), new Dimension( 100, 50 ), 0, 0 );
        assertEquals( new Rectangle( 50, 0, 50, 50 ), gap );
    }

    @Test
    public void shouldFindGapAtOrigin() {
        Component c1 = new JPanel();
        c1.setBounds( 50, 0, 50, 50 );
        Rectangle gap = layouter.findGap( Arrays.asList( c1 ), new Dimension( 100, 50 ), 0, 0 );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), gap );
    }

    @Test
    public void shouldFindSimpleGapWithHGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Rectangle gap = layouter.findGap( Arrays.asList( c1 ), new Dimension( 102, 50 ), 2, 0 );
        assertEquals( new Rectangle( 52, 0, 50, 50 ), gap );
    }

    @Test
    public void shouldFindGapAtOriginWithHGap() {
        Component c1 = new JPanel();
        c1.setBounds( 52, 0, 50, 50 );
        Rectangle gap = layouter.findGap( Arrays.asList( c1 ), new Dimension( 102, 50 ), 2, 0 );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), gap );
    }

    @Test
    public void shouldFindGapInSquare() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 55, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 55, 55, 50, 50 );
        Rectangle gap = layouter.findGap( Arrays.asList( c1, c2, c3 ), new Dimension( 105, 105 ), 5, 5 );
        assertEquals( new Rectangle( 55, 0, 50, 50 ), gap );
    }
    
    @Test
    public void shouldFindGapInMoreComplexGrid() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 55, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 55, 55, 50, 50 );
        Component c4 = new JPanel();
        c4.setBounds( 85, 0, 15, 50 );
        Rectangle gap = layouter.findGap( Arrays.asList( c1, c2, c3, c4 ), new Dimension( 105, 105 ), 5, 5 );
        assertEquals( new Rectangle( 55, 0, 25, 50 ), gap );
    }
}
