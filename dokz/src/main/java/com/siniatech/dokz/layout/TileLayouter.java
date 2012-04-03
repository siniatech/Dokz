package com.siniatech.dokz.layout;

import static java.lang.Math.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileLayouter extends AbstractLayouter {

    @Override
    public void doLayout( List<Component> components, Dimension size, int hGap, int vGap ) {
        int xSide = (int) ceil( sqrt( components.size() ) );
        if ( xSide != 0 ) {
            Map<Point, Rectangle> coordToBounds = determineBoundsOfGrid( components, size, hGap, vGap, xSide );
            assignBoundsToComponents( components, coordToBounds, xSide - 1 );
        }
    }

    private Map<Point, Rectangle> determineBoundsOfGrid( List<Component> components, Dimension size, int hGap, int vGap, int xSide ) {
        int ySide = components.size() > ( ( xSide * xSide ) - xSide ) ? xSide : ( xSide - 1 );
        int xOfLastComp = ( xSide * ySide ) != components.size() ? xSide - 1 - ( ( xSide * ySide ) - components.size() ) : -1;
        int endCol = xSide - 1;
        int endRow = ySide - 1;
        int compWidth = ( size.width - ( hGap * ( xSide - 1 ) ) ) / xSide;
        int compHeight = ( size.height - ( vGap * ( ySide - 1 ) ) ) / ySide;
        int rowEndCompWidth = ( size.width - ( hGap * ( xSide - 1 ) ) ) - ( endCol * compWidth );
        int colEndCompHeight = ( size.height - ( vGap * ( ySide - 1 ) ) ) - ( endRow * compHeight );
        Map<Point, Rectangle> coordToBounds = new HashMap<>();
        for ( int i = 0; i < xSide; i++ ) {
            for ( int j = 0; j < ySide; j++ ) {
                int x = ( compWidth + hGap ) * i;
                int y = ( compHeight + vGap ) * j;
                final int w;
                if ( j == endRow && i == xOfLastComp ) {
                    w = size.width - x;
                } else {
                    w = ( i == endCol ) ? rowEndCompWidth : compWidth;
                }
                int h = ( j == endRow ) ? colEndCompHeight : compHeight;
                coordToBounds.put( new Point( i, j ), new Rectangle( x, y, w, h ) );
            }
        }
        return coordToBounds;
    }

    private void assignBoundsToComponents( List<Component> components, Map<Point, Rectangle> coordToBounds, int endCol ) {
        int x = 0;
        int y = 0;
        for ( Component component : components ) {
            component.setBounds( coordToBounds.get( new Point( x, y ) ) );
            y = x == endCol ? y + 1 : y;
            x = x == endCol ? 0 : x + 1;
        }
    }
}
