package com.siniatech.dokz.context;

import com.siniatech.dokz.DokzPanel;

public class NeighbourContext {

    private DokzPanel north;
    private DokzPanel south;
    private DokzPanel east;
    private DokzPanel west;
    private DokzPanel point;

    public NeighbourContext( DokzPanel north, DokzPanel south, DokzPanel east, DokzPanel west, DokzPanel point ) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.point = point;
    }

    public DokzPanel getNorth() {
        return north;
    }

    public DokzPanel getSouth() {
        return south;
    }

    public DokzPanel getEast() {
        return east;
    }

    public DokzPanel getWest() {
        return west;
    }

    public DokzPanel getPoint() {
        return point;
    }

}