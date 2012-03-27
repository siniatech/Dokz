package com.siniatech.dokz;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;

import com.siniatech.siniautils.fn.IResponse0;

public class DokzPanelContext {

    private DokzPanelState state;
    private JCheckBoxMenuItem menuItem;
    private DokzContainer container;

    public DokzPanelContext( String title, final IResponse0 openFunction, final IResponse0 closeFunction ) {
        menuItem = new JCheckBoxMenuItem( title );
        menuItem.addActionListener( new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if ( state == DokzPanelState.closed ) {
                    openFunction.respond();
                } else {
                    closeFunction.respond();
                }
            }
        } );
        setState( DokzPanelState.open );
    }

    public DokzPanelState getState() {
        return state;
    }

    public void setState( DokzPanelState state ) {
        this.state = state;
        menuItem.setSelected( state != DokzPanelState.closed );
    }

    public JMenuItem getPanelStateMenuItem() {
        return menuItem;
    }

    public void setVisibleIn( DokzContainer container ) {
        this.container = container;
    }

    public boolean isVisibleIn( DokzContainer container ) {
        return this.container == container;
    }

    public Rectangle getBounds() {
        return new Rectangle( 0, 0, 100, 100 );
    }

}
