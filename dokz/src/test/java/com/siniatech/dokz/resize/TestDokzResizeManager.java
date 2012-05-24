package com.siniatech.dokz.resize;

import static com.siniatech.dokz.DokzConstants.*;
import static com.siniatech.siniautils.collection.SetHelper.*;
import static junit.framework.Assert.*;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;

import org.junit.Test;

import com.siniatech.dokz.DokzNeighbourContext;
import com.siniatech.dokz.DokzPanel;
import com.siniatech.dokz.test.fixtures.MockDokzPanel;
import com.siniatech.siniautils.fn.ResponseHelper;

public class TestDokzResizeManager {

    static private final Set<DokzPanel> emptySet = emptySet();
    static private final int inverseMinPanelWidth = 1000 - minPanelWidth;
    static private final int inverseMinPanelHeight = 1000 - minPanelHeight;

    private DokzResizeManager dokzResizeManager = new DokzResizeManager( ResponseHelper.emptyResponse0 );

    @Test
    public void shouldKnowWhenResizing() {
        assertFalse( dokzResizeManager.isResizeStarted() );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, new MockDokzPanel(), new MockDokzPanel(), null );
        dokzResizeManager.startResize( new Point( 0, 0 ), neighbourContext, emptySet );
        assertTrue( dokzResizeManager.isResizeStarted() );
        dokzResizeManager.endResize();
        assertFalse( dokzResizeManager.isResizeStarted() );
    }

    @Test
    public void cannotResizeIfOverPanel() {
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, new MockDokzPanel(), new MockDokzPanel(), new MockDokzPanel() );
        assertFalse( dokzResizeManager.canStartResize( neighbourContext ) );
    }

    @Test
    public void cannotResizeIfNoNeighbours() {
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, null, null, null );
        assertFalse( dokzResizeManager.canStartResize( neighbourContext ) );
    }

    @Test
    public void cannotResizeIfOneEwNeighbour() {
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, new MockDokzPanel(), null, null );
        assertFalse( dokzResizeManager.canStartResize( neighbourContext ) );
    }

    @Test
    public void cannotResizeIfOneNsNeighbour() {
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, new MockDokzPanel(), null, null, null );
        assertFalse( dokzResizeManager.canStartResize( neighbourContext ) );
    }

    @Test
    public void canResizeIfHaveEwNeighbours() {
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, new MockDokzPanel(), new MockDokzPanel(), null );
        assertTrue( dokzResizeManager.canStartResize( neighbourContext ) );
    }

    @Test
    public void canResizeIfHaveNsNeighbours() {
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( new MockDokzPanel(), new MockDokzPanel(), null, null, null );
        assertTrue( dokzResizeManager.canStartResize( neighbourContext ) );
    }

    @Test
    public void canResizeE() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 500, 0, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, p2, p1, null );
        dokzResizeManager.startResize( new Point( 500, 250 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 550, 250 ) );
        assertEquals( new Rectangle( 0, 0, 550, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 550, 0, 450, 500 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 600, 250 ) );
        assertEquals( new Rectangle( 0, 0, 600, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 600, 0, 400, 500 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 600, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 600, 0, 400, 500 ), p2.getBounds() );
    }

    @Test
    public void cannotResizeESmallerThanMin() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 500, 0, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, p2, p1, null );
        dokzResizeManager.startResize( new Point( 500, 250 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( inverseMinPanelWidth, 250 ) );
        assertEquals( new Rectangle( 0, 0, inverseMinPanelWidth, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( inverseMinPanelWidth, 0, minPanelWidth, 500 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( inverseMinPanelWidth + 1, 250 ) );
        assertEquals( new Rectangle( 0, 0, inverseMinPanelWidth, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( inverseMinPanelWidth, 0, minPanelWidth, 500 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( inverseMinPanelWidth + 20, 250 ) );
        assertEquals( new Rectangle( 0, 0, inverseMinPanelWidth, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( inverseMinPanelWidth, 0, minPanelWidth, 500 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, inverseMinPanelWidth, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( inverseMinPanelWidth, 0, minPanelWidth, 500 ), p2.getBounds() );
    }

    @Test
    public void canResizeEWithGap() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 505, 0, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, p2, p1, null );
        dokzResizeManager.startResize( new Point( 502, 250 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 552, 250 ) );
        assertEquals( new Rectangle( 0, 0, 550, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 555, 0, 450, 500 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 602, 250 ) );
        assertEquals( new Rectangle( 0, 0, 600, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 605, 0, 400, 500 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 600, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 605, 0, 400, 500 ), p2.getBounds() );
    }

    @Test
    public void canResizeEYChangesMakesNoDiff() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 500, 0, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, p2, p1, null );
        dokzResizeManager.startResize( new Point( 500, 250 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 550, 50 ) );
        assertEquals( new Rectangle( 0, 0, 550, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 550, 0, 450, 500 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 600, 450 ) );
        assertEquals( new Rectangle( 0, 0, 600, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 600, 0, 400, 500 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 600, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 600, 0, 400, 500 ), p2.getBounds() );
    }

    @Test
    public void canResizeW() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 500, 0, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, p2, p1, null );
        dokzResizeManager.startResize( new Point( 500, 250 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 450, 250 ) );
        assertEquals( new Rectangle( 0, 0, 450, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 450, 0, 550, 500 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 300, 250 ) );
        assertEquals( new Rectangle( 0, 0, 300, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 300, 0, 700, 500 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 300, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 300, 0, 700, 500 ), p2.getBounds() );
    }

    @Test
    public void cannotResizeWSmallerThanMin() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 500, 0, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, p2, p1, null );
        dokzResizeManager.startResize( new Point( 500, 250 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( minPanelWidth, 250 ) );
        assertEquals( new Rectangle( 0, 0, minPanelWidth, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( minPanelWidth, 0, inverseMinPanelWidth, 500 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( minPanelWidth - 1, 250 ) );
        assertEquals( new Rectangle( 0, 0, minPanelWidth, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( minPanelWidth, 0, inverseMinPanelWidth, 500 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( minPanelWidth - 20, 250 ) );
        assertEquals( new Rectangle( 0, 0, minPanelWidth, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( minPanelWidth, 0, inverseMinPanelWidth, 500 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, minPanelWidth, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( minPanelWidth, 0, inverseMinPanelWidth, 500 ), p2.getBounds() );
    }

    @Test
    public void canResizeWWithGap() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 505, 0, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, p2, p1, null );
        dokzResizeManager.startResize( new Point( 502, 250 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 452, 250 ) );
        assertEquals( new Rectangle( 0, 0, 450, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 455, 0, 550, 500 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 202, 250 ) );
        assertEquals( new Rectangle( 0, 0, 200, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 205, 0, 800, 500 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 200, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 205, 0, 800, 500 ), p2.getBounds() );
    }

    @Test
    public void canResizeWYChangesMakesNoDiff() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 500, 0, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, p2, p1, null );
        dokzResizeManager.startResize( new Point( 500, 250 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 450, 200 ) );
        assertEquals( new Rectangle( 0, 0, 450, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 450, 0, 550, 500 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 300, 120 ) );
        assertEquals( new Rectangle( 0, 0, 300, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 300, 0, 700, 500 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 300, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 300, 0, 700, 500 ), p2.getBounds() );
    }

    @Test
    public void canResizeEWInOneMove() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 500, 0, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, p2, p1, null );
        dokzResizeManager.startResize( new Point( 500, 250 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 450, 250 ) );
        assertEquals( new Rectangle( 0, 0, 450, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 450, 0, 550, 500 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 800, 250 ) );
        assertEquals( new Rectangle( 0, 0, 800, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 800, 0, 200, 500 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 800, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 800, 0, 200, 500 ), p2.getBounds() );
    }

    @Test
    public void canResizeN() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 0, 500, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( p1, p2, null, null, null );
        dokzResizeManager.startResize( new Point( 250, 500 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 250, 250 ) );
        assertEquals( new Rectangle( 0, 0, 500, 250 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 250, 500, 750 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 250, 400 ) );
        assertEquals( new Rectangle( 0, 0, 500, 400 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 400, 500, 600 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 500, 400 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 400, 500, 600 ), p2.getBounds() );
    }

    @Test
    public void cannotResizeNSmallerThanMin() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 0, 500, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( p1, p2, null, null, null );
        dokzResizeManager.startResize( new Point( 250, 500 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 250, minPanelHeight ) );
        assertEquals( new Rectangle( 0, 0, 500, minPanelHeight ), p1.getBounds() );
        assertEquals( new Rectangle( 0, minPanelHeight, 500, inverseMinPanelHeight ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 250, minPanelHeight - 1 ) );
        assertEquals( new Rectangle( 0, 0, 500, minPanelHeight ), p1.getBounds() );
        assertEquals( new Rectangle( 0, minPanelHeight, 500, inverseMinPanelHeight ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 250, minPanelHeight - 20 ) );
        assertEquals( new Rectangle( 0, 0, 500, minPanelHeight ), p1.getBounds() );
        assertEquals( new Rectangle( 0, minPanelHeight, 500, inverseMinPanelHeight ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 500, minPanelHeight ), p1.getBounds() );
        assertEquals( new Rectangle( 0, minPanelHeight, 500, inverseMinPanelHeight ), p2.getBounds() );
    }

    @Test
    public void canResizeNWithGap() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 0, 505, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( p1, p2, null, null, null );
        dokzResizeManager.startResize( new Point( 250, 504 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 250, 154 ) );
        assertEquals( new Rectangle( 0, 0, 500, 150 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 155, 500, 850 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 250, 304 ) );
        assertEquals( new Rectangle( 0, 0, 500, 300 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 305, 500, 700 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 500, 300 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 305, 500, 700 ), p2.getBounds() );
    }

    @Test
    public void canResizeNXChangesMakesNoDiff() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 0, 500, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( p1, p2, null, null, null );
        dokzResizeManager.startResize( new Point( 250, 500 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( -12, 350 ) );
        assertEquals( new Rectangle( 0, 0, 500, 350 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 350, 500, 650 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 700, 100 ) );
        assertEquals( new Rectangle( 0, 0, 500, 100 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 100, 500, 900 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 500, 100 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 100, 500, 900 ), p2.getBounds() );
    }

    @Test
    public void canResizeS() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 0, 500, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( p1, p2, null, null, null );
        dokzResizeManager.startResize( new Point( 250, 500 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 250, 550 ) );
        assertEquals( new Rectangle( 0, 0, 500, 550 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 550, 500, 450 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 250, 600 ) );
        assertEquals( new Rectangle( 0, 0, 500, 600 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 600, 500, 400 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 500, 600 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 600, 500, 400 ), p2.getBounds() );
    }

    @Test
    public void cannotResizeSSmallerThanMin() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 0, 500, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( p1, p2, null, null, null );
        dokzResizeManager.startResize( new Point( 250, 500 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 250, inverseMinPanelHeight ) );
        assertEquals( new Rectangle( 0, 0, 500, inverseMinPanelHeight ), p1.getBounds() );
        assertEquals( new Rectangle( 0, inverseMinPanelHeight, 500, minPanelHeight ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 250, inverseMinPanelHeight + 1 ) );
        assertEquals( new Rectangle( 0, 0, 500, inverseMinPanelHeight ), p1.getBounds() );
        assertEquals( new Rectangle( 0, inverseMinPanelHeight, 500, minPanelHeight ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 250, inverseMinPanelHeight + 20 ) );
        assertEquals( new Rectangle( 0, 0, 500, inverseMinPanelHeight ), p1.getBounds() );
        assertEquals( new Rectangle( 0, inverseMinPanelHeight, 500, minPanelHeight ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 500, inverseMinPanelHeight ), p1.getBounds() );
        assertEquals( new Rectangle( 0, inverseMinPanelHeight, 500, minPanelHeight ), p2.getBounds() );
    }

    @Test
    public void canResizeSWithGap() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 0, 505, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( p1, p2, null, null, null );
        dokzResizeManager.startResize( new Point( 250, 504 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 250, 554 ) );
        assertEquals( new Rectangle( 0, 0, 500, 550 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 555, 500, 450 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 250, 604 ) );
        assertEquals( new Rectangle( 0, 0, 500, 600 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 605, 500, 400 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 500, 600 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 605, 500, 400 ), p2.getBounds() );
    }

    @Test
    public void canResizeSXChangesMakesNoDiff() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 0, 500, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( p1, p2, null, null, null );
        dokzResizeManager.startResize( new Point( 250, 500 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( -12, 550 ) );
        assertEquals( new Rectangle( 0, 0, 500, 550 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 550, 500, 450 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 700, 600 ) );
        assertEquals( new Rectangle( 0, 0, 500, 600 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 600, 500, 400 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 500, 600 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 600, 500, 400 ), p2.getBounds() );
    }

    @Test
    public void canResizeNSInOneMove() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 0, 500, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( p1, p2, null, null, null );
        dokzResizeManager.startResize( new Point( 250, 500 ), neighbourContext, asSet( p1, p2 ) );
        dokzResizeManager.doResize( new Point( 250, 550 ) );
        assertEquals( new Rectangle( 0, 0, 500, 550 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 550, 500, 450 ), p2.getBounds() );
        dokzResizeManager.doResize( new Point( 250, 200 ) );
        assertEquals( new Rectangle( 0, 0, 500, 200 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 200, 500, 800 ), p2.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 500, 200 ), p1.getBounds() );
        assertEquals( new Rectangle( 0, 200, 500, 800 ), p2.getBounds() );
    }

    @Test
    public void resizeSAffectsAdjacentPanels() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 500, 0, 500, 500 ) );
        DokzPanel p3 = new MockDokzPanel( new Rectangle( 0, 500, 500, 500 ) );
        DokzPanel p4 = new MockDokzPanel( new Rectangle( 500, 500, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( p1, p3, null, null, null );
        dokzResizeManager.startResize( new Point( 250, 500 ), neighbourContext, asSet( p1, p2, p3, p4 ) );
        dokzResizeManager.doResize( new Point( 250, 550 ) );
        assertEquals( new Rectangle( 0, 0, 500, 550 ), p1.getBounds() );
        assertEquals( new Rectangle( 500, 0, 500, 550 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 550, 500, 450 ), p3.getBounds() );
        assertEquals( new Rectangle( 500, 550, 500, 450 ), p4.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 500, 550 ), p1.getBounds() );
        assertEquals( new Rectangle( 500, 0, 500, 550 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 550, 500, 450 ), p3.getBounds() );
        assertEquals( new Rectangle( 500, 550, 500, 450 ), p4.getBounds() );
    }

    @Test
    public void resizeNAffectsAdjacentPanels() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 500, 0, 500, 500 ) );
        DokzPanel p3 = new MockDokzPanel( new Rectangle( 0, 500, 500, 500 ) );
        DokzPanel p4 = new MockDokzPanel( new Rectangle( 500, 500, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( p1, p3, null, null, null );
        dokzResizeManager.startResize( new Point( 250, 500 ), neighbourContext, asSet( p1, p2, p3, p4 ) );
        dokzResizeManager.doResize( new Point( 250, 350 ) );
        assertEquals( new Rectangle( 0, 0, 500, 350 ), p1.getBounds() );
        assertEquals( new Rectangle( 500, 0, 500, 350 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 350, 500, 650 ), p3.getBounds() );
        assertEquals( new Rectangle( 500, 350, 500, 650 ), p4.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 500, 350 ), p1.getBounds() );
        assertEquals( new Rectangle( 500, 0, 500, 350 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 350, 500, 650 ), p3.getBounds() );
        assertEquals( new Rectangle( 500, 350, 500, 650 ), p4.getBounds() );
    }

    @Test
    public void resizeWAffectsAdjacentPanels() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 500, 0, 500, 500 ) );
        DokzPanel p3 = new MockDokzPanel( new Rectangle( 0, 500, 500, 500 ) );
        DokzPanel p4 = new MockDokzPanel( new Rectangle( 500, 500, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, p2, p1, null );
        dokzResizeManager.startResize( new Point( 500, 750 ), neighbourContext, asSet( p1, p2, p3, p4 ) );
        dokzResizeManager.doResize( new Point( 400, 750 ) );
        assertEquals( new Rectangle( 0, 0, 400, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 400, 0, 600, 500 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 500, 400, 500 ), p3.getBounds() );
        assertEquals( new Rectangle( 400, 500, 600, 500 ), p4.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 400, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 400, 0, 600, 500 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 500, 400, 500 ), p3.getBounds() );
        assertEquals( new Rectangle( 400, 500, 600, 500 ), p4.getBounds() );
    }

    @Test
    public void resizeEAffectsAdjacentPanels() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 500, 0, 500, 500 ) );
        DokzPanel p3 = new MockDokzPanel( new Rectangle( 0, 500, 500, 500 ) );
        DokzPanel p4 = new MockDokzPanel( new Rectangle( 500, 500, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, p2, p1, null );
        dokzResizeManager.startResize( new Point( 500, 750 ), neighbourContext, asSet( p1, p2, p3, p4 ) );
        dokzResizeManager.doResize( new Point( 800, 750 ) );
        assertEquals( new Rectangle( 0, 0, 800, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 800, 0, 200, 500 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 500, 800, 500 ), p3.getBounds() );
        assertEquals( new Rectangle( 800, 500, 200, 500 ), p4.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 800, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 800, 0, 200, 500 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 500, 800, 500 ), p3.getBounds() );
        assertEquals( new Rectangle( 800, 500, 200, 500 ), p4.getBounds() );
    }

    @Test
    public void resizeSAffectsAdjacentPanelsWithGap() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 505, 0, 500, 500 ) );
        DokzPanel p3 = new MockDokzPanel( new Rectangle( 0, 505, 500, 500 ) );
        DokzPanel p4 = new MockDokzPanel( new Rectangle( 505, 505, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( p1, p3, null, null, null );
        dokzResizeManager.startResize( new Point( 250, 502 ), neighbourContext, asSet( p1, p2, p3, p4 ) );
        dokzResizeManager.doResize( new Point( 250, 552 ) );
        assertEquals( new Rectangle( 0, 0, 500, 550 ), p1.getBounds() );
        assertEquals( new Rectangle( 505, 0, 500, 550 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 555, 500, 450 ), p3.getBounds() );
        assertEquals( new Rectangle( 505, 555, 500, 450 ), p4.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 500, 550 ), p1.getBounds() );
        assertEquals( new Rectangle( 505, 0, 500, 550 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 555, 500, 450 ), p3.getBounds() );
        assertEquals( new Rectangle( 505, 555, 500, 450 ), p4.getBounds() );
    }

    @Test
    public void resizeNAffectsAdjacentPanelsWithGap() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 505, 0, 500, 500 ) );
        DokzPanel p3 = new MockDokzPanel( new Rectangle( 0, 505, 500, 500 ) );
        DokzPanel p4 = new MockDokzPanel( new Rectangle( 505, 505, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( p1, p3, null, null, null );
        dokzResizeManager.startResize( new Point( 250, 501 ), neighbourContext, asSet( p1, p2, p3, p4 ) );
        dokzResizeManager.doResize( new Point( 250, 351 ) );
        assertEquals( new Rectangle( 0, 0, 500, 350 ), p1.getBounds() );
        assertEquals( new Rectangle( 505, 0, 500, 350 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 355, 500, 650 ), p3.getBounds() );
        assertEquals( new Rectangle( 505, 355, 500, 650 ), p4.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 500, 350 ), p1.getBounds() );
        assertEquals( new Rectangle( 505, 0, 500, 350 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 355, 500, 650 ), p3.getBounds() );
        assertEquals( new Rectangle( 505, 355, 500, 650 ), p4.getBounds() );
    }

    @Test
    public void resizeWAffectsAdjacentPanelsWithGap() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 505, 0, 500, 500 ) );
        DokzPanel p3 = new MockDokzPanel( new Rectangle( 0, 505, 500, 500 ) );
        DokzPanel p4 = new MockDokzPanel( new Rectangle( 505, 505, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, p2, p1, null );
        dokzResizeManager.startResize( new Point( 500, 750 ), neighbourContext, asSet( p1, p2, p3, p4 ) );
        dokzResizeManager.doResize( new Point( 400, 750 ) );
        assertEquals( new Rectangle( 0, 0, 400, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 405, 0, 600, 500 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 505, 400, 500 ), p3.getBounds() );
        assertEquals( new Rectangle( 405, 505, 600, 500 ), p4.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 400, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 405, 0, 600, 500 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 505, 400, 500 ), p3.getBounds() );
        assertEquals( new Rectangle( 405, 505, 600, 500 ), p4.getBounds() );
    }

    @Test
    public void resizeEAffectsAdjacentPanelsWithGap() {
        DokzPanel p1 = new MockDokzPanel( new Rectangle( 0, 0, 500, 500 ) );
        DokzPanel p2 = new MockDokzPanel( new Rectangle( 505, 0, 500, 500 ) );
        DokzPanel p3 = new MockDokzPanel( new Rectangle( 0, 505, 500, 500 ) );
        DokzPanel p4 = new MockDokzPanel( new Rectangle( 505, 505, 500, 500 ) );
        DokzNeighbourContext neighbourContext = new DokzNeighbourContext( null, null, p2, p1, null );
        dokzResizeManager.startResize( new Point( 500, 753 ), neighbourContext, asSet( p1, p2, p3, p4 ) );
        dokzResizeManager.doResize( new Point( 800, 753 ) );
        assertEquals( new Rectangle( 0, 0, 800, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 805, 0, 200, 500 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 505, 800, 500 ), p3.getBounds() );
        assertEquals( new Rectangle( 805, 505, 200, 500 ), p4.getBounds() );
        dokzResizeManager.endResize();
        assertEquals( new Rectangle( 0, 0, 800, 500 ), p1.getBounds() );
        assertEquals( new Rectangle( 805, 0, 200, 500 ), p2.getBounds() );
        assertEquals( new Rectangle( 0, 505, 800, 500 ), p3.getBounds() );
        assertEquals( new Rectangle( 805, 505, 200, 500 ), p4.getBounds() );
    }

}
