package com.siniatech.dokz;

import javax.swing.JComponent;

import com.siniatech.dokz.api.IDokzContext;
import com.siniatech.dokz.component.DokzButtonBar;
import com.siniatech.dokz.context.DokzContext;
import com.siniatech.dokz.context.DokzPanelContext;
import com.siniatech.siniautils.fn.IResponse0;
import com.siniatech.siniautils.swing.IAmJComponent;

public class DokzManager implements IAmJComponent {

    private DokzContext dokzContext;

    public DokzManager() {
        dokzContext = new DokzContext();
        final DokzContainer mainContainer = new DokzContainer( dokzContext );
        dokzContext.setMainContainer( mainContainer );

    }

    JComponent createButtonBarFor( final DokzPanel dokzPanel, String title ) {
        IResponse0 onMax = new IResponse0() {
            @Override
            public void respond() {
                dokzContext.toggleMaximized( dokzPanel );
                dokzContext.getContainerFor(dokzPanel).revalidate();
            }
        };
        IResponse0 onClose = new IResponse0() {
            @Override
            public void respond() {
                close( dokzPanel );
            }
        };
        IResponse0 onMin = new IResponse0() {
            @Override
            public void respond() {
                System.out.println( "MIN" );
            }
        };
        return new DokzButtonBar( title, onMin, onMax, onClose );
    }

    @Override
    public JComponent asJComponent() {
        return dokzContext.getMainContainer();
    }

    private void close( final DokzPanel dokzPanel ) {
        dokzContext.getMainContainer().remove( dokzPanel );
        dokzContext.getMainContainer().revalidate();
        dokzContext.getPanelContext( dokzPanel ).setState( DokzPanelState.closed );
    }

    private void open( final DokzPanel dokzPanel ) {
        dokzContext.getMainContainer().add( dokzPanel );
        dokzContext.getMainContainer().validate();
        dokzContext.getPanelContext( dokzPanel ).setState( DokzPanelState.open );
    }

    public void add( JComponent component, String title ) {
        final DokzPanel dokzPanel = new DokzPanel( this, component, title );
        dokzContext.add( dokzPanel, dokzContext.getMainContainer(), new DokzPanelContext( title, new IResponse0() {
            @Override
            public void respond() {
                open( dokzPanel );
            }
        }, new IResponse0() {
            @Override
            public void respond() {
                close( dokzPanel );
            }
        } ) );
    }

    public void add( JComponent component ) {
        add( component, null );
    }

    public IDokzContext getDokzContext() {
        return dokzContext;
    }
}
