package com.siniatech.dokz.layout;

import static junit.framework.Assert.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Arrays;

import javax.swing.JPanel;

import org.junit.Test;

public class TestScalingLayouter {

    private ScalingLayouter layouter = new ScalingLayouter();

    @Test
    public void shouldLayoutEmptyContainer() {
        layouter.doLayout( Arrays.<Component> asList(), new Dimension( 200, 200 ) );
    }

    @Test
    public void shouldScaleUpSingleComponent() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1 ), new Dimension( 100, 100 ) );
        assertEquals( new Rectangle( 0, 0, 100, 100 ), c1.getBounds() );
    }

    @Test
    public void shouldScaleDownSingleComponent() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1 ), new Dimension( 25, 25 ) );
        assertEquals( new Rectangle( 0, 0, 25, 25 ), c1.getBounds() );
    }

    @Test
    public void shouldScaleSimpleHorizontalStructure() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 200, 200 ) );
        assertEquals( new Rectangle( 0, 0, 100, 200 ), c1.getBounds() );
        assertEquals( new Rectangle( 100, 0, 100, 200 ), c2.getBounds() );
    }

    @Test
    public void shouldScaleSimpleVerticalStructure() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 200, 200 ) );
        assertEquals( new Rectangle( 0, 0, 200, 100 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 100, 200, 100 ), c2.getBounds() );
    }

    @Test
    public void shouldScaleUpSimpleHorizontalStructureAndMaintainGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 52, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 202, 200 ), 2, 0 );
        assertEquals( new Rectangle( 0, 0, 100, 200 ), c1.getBounds() );
        assertEquals( new Rectangle( 102, 0, 100, 200 ), c2.getBounds() );
    }

    @Test
    public void shouldScaleUpSimpleHorizontalStructureAndChangeGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 52, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 204, 200 ), 4, 0 );
        assertEquals( new Rectangle( 0, 0, 100, 200 ), c1.getBounds() );
        assertEquals( new Rectangle( 104, 0, 100, 200 ), c2.getBounds() );
    }

    @Test
    public void shouldScaleDownSimpleHorizontalStructureAndMaintainGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 100, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 102, 0, 100, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 102, 50 ), 2, 0 );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 52, 0, 50, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldScaleDownSimpleHorizontalStructureAndChangeGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 100, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 102, 0, 100, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 101, 50 ), 1, 0 );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 51, 0, 50, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldScaleUpSimpleVerticalStructureAndMaintainGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 52, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 200, 202 ), 0, 2 );
        assertEquals( new Rectangle( 0, 0, 200, 100 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 102, 200, 100 ), c2.getBounds() );
    }

    @Test
    public void shouldScaleUpSimpleVerticalStructureAndChangeGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 52, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 200, 204 ), 0, 4 );
        assertEquals( new Rectangle( 0, 0, 200, 100 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 104, 200, 100 ), c2.getBounds() );
    }

    @Test
    public void shouldScaleDownSimpleVerticalStructureAndMaintainGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 100 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 102, 50, 100 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 50, 102 ), 0, 2 );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 52, 50, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldScaleDownSimpleVerticalStructureAndChangeGap() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 100 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 102, 50, 100 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 50, 101 ), 0, 1 );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 51, 50, 50 ), c2.getBounds() );
    }

    @Test
    public void shouldScaleUpInOneDirAndDownInTheOther() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 0, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2 ), new Dimension( 200, 30 ) );
        assertEquals( new Rectangle( 0, 0, 100, 30 ), c1.getBounds() );
        assertEquals( new Rectangle( 100, 0, 100, 30 ), c2.getBounds() );
    }

    @Test
    public void shouldntChangeSquareOfSameSize() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 0, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 0, 50, 50, 50 );
        Component c4 = new JPanel();
        c4.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4 ), new Dimension( 100, 100 ) );
        assertEquals( new Rectangle( 0, 0, 50, 50 ), c1.getBounds() );
        assertEquals( new Rectangle( 50, 0, 50, 50 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 50, 50, 50 ), c3.getBounds() );
        assertEquals( new Rectangle( 50, 50, 50, 50 ), c4.getBounds() );
    }

    @Test
    public void shouldScaleASquareUp() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 0, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 0, 50, 50, 50 );
        Component c4 = new JPanel();
        c4.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4 ), new Dimension( 200, 200 ) );
        assertEquals( new Rectangle( 0, 0, 100, 100 ), c1.getBounds() );
        assertEquals( new Rectangle( 100, 0, 100, 100 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 100, 100, 100 ), c3.getBounds() );
        assertEquals( new Rectangle( 100, 100, 100, 100 ), c4.getBounds() );
    }

    @Test
    public void shouldScaleASquareDown() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 0, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 0, 50, 50, 50 );
        Component c4 = new JPanel();
        c4.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4 ), new Dimension( 50, 50 ) );
        assertEquals( new Rectangle( 0, 0, 25, 25 ), c1.getBounds() );
        assertEquals( new Rectangle( 25, 0, 25, 25 ), c2.getBounds() );
        assertEquals( new Rectangle( 0, 25, 25, 25 ), c3.getBounds() );
        assertEquals( new Rectangle( 25, 25, 25, 25 ), c4.getBounds() );
    }

    @Test
    public void shouldScaleThreePaneH() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 100, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 0, 50, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3 ), new Dimension( 200, 200 ) );
        assertEquals( new Rectangle( 0, 0, 200, 100 ), c1.getBounds() );
        assertEquals( new Rectangle( 0, 100, 100, 100 ), c2.getBounds() );
        assertEquals( new Rectangle( 100, 100, 100, 100 ), c3.getBounds() );
    }

    @Test
    public void shouldScaleThreePaneV() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 100 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 0, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 50, 50, 50, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3 ), new Dimension( 200, 200 ) );
        assertEquals( new Rectangle( 0, 0, 100, 200 ), c1.getBounds() );
        assertEquals( new Rectangle( 100, 0, 100, 100 ), c2.getBounds() );
        assertEquals( new Rectangle( 100, 100, 100, 100 ), c3.getBounds() );
    }
    
    @Test
    public void testObservedBug() {
        Component c1 = new JPanel();
        c1.setBounds( 0, 0, 50, 50 );
        Component c2 = new JPanel();
        c2.setBounds( 50, 0, 50, 50 );
        Component c3 = new JPanel();
        c3.setBounds( 100, 0, 50, 50 );
        Component c4 = new JPanel();
        c4.setBounds( 0, 50, 50, 50 );
        Component c5 = new JPanel();
        c5.setBounds( 50, 50, 50, 50 );
        Component c6 = new JPanel();
        c6.setBounds( 100, 50, 50, 50 );
        Component c7 = new JPanel();
        c7.setBounds( 0, 100, 50, 50 );
        Component c8 = new JPanel();
        c8.setBounds( 50, 100, 100, 50 );
        layouter.doLayout( Arrays.asList( c1, c2, c3, c4, c5, c6, c7, c8 ), new Dimension( 300, 300 ) );
        assertEquals( new Rectangle( 0, 0, 100, 100 ), c1.getBounds() );
        assertEquals( new Rectangle( 100, 0, 100, 100 ), c2.getBounds() );
        assertEquals( new Rectangle( 200, 0, 100, 100 ), c3.getBounds() );
        assertEquals( new Rectangle( 0, 100, 100, 100 ), c4.getBounds() );
        assertEquals( new Rectangle( 100, 100, 100, 100 ), c5.getBounds() );
        assertEquals( new Rectangle( 200, 100, 100, 100 ), c6.getBounds() );
        assertEquals( new Rectangle( 0, 200, 100, 100 ), c7.getBounds() );
        assertEquals( new Rectangle( 100, 200, 200, 100 ), c8.getBounds() );
    }
}
