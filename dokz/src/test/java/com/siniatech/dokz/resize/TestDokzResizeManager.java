package com.siniatech.dokz.resize;

import static com.siniatech.siniautils.collection.SetHelper.*;
import static junit.framework.Assert.*;

import java.awt.Point;
import java.util.Set;

import org.junit.Test;

import com.siniatech.dokz.DokzNeighbourContext;
import com.siniatech.dokz.DokzPanel;
import com.siniatech.dokz.test.fixtures.MockDokzPanel;
import com.siniatech.siniautils.fn.IResponse0;

public class TestDokzResizeManager {

    static private final Set<DokzPanel> emptySet = emptySet();

    private DokzResizeManager dokzResizeManager = new DokzResizeManager( IResponse0.Utils.emptyResponse );

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
}
