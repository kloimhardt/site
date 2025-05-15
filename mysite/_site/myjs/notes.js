class Notes {

    out() { return this.pitch;};

    plotpoints() {
        var pp = statejs["frequencies"];
        pp.map((d, i) => {
            statejs['bnotes1'].create('point', [d[1], i],
                                      {name:d[0],
                                       color: "black"});
        });

        statejs['bnotes1'].create('functiongraph', ['(7 * (log(x) - log(16.35)))/log(2)'], {strokeColor:'red'});

        var violines = [30, 32, 34, 36, 38];
        violines.map((y) =>
            statejs['bnotes1'].create('line',[[150, y],
                                              [920, y]],
                                      {straightFirst:false,
                                       straightLast:false,
                                       strokeWidth:1,
                                       strokeColor:'black'}));

        var setAtt = (att) => (idx) =>
            statejs.bnotes1.select(statejs["frequencies"][idx][0]).setAttribute(att);

        violines.map(setAtt({size: 9}));
        var viogaps = [28, 29, 31, 33, 35, 37, 39, 40];
        viogaps.map(setAtt({size: 9}));

        statejs['bnotes1'].create('image',["https://upload.wikimedia.org/wikipedia/commons/f/ff/GClef.svg", [170,26.4], [80,15.5]]);
    }

    main(divid) {
        statejs['bnotes1'] = JXG.JSXGraph.initBoard(divid, {
            boundingbox: [120, 45, 950, 20],
            axis:false,
            grid: false
        });

        this.plotpoints();
    }
};

var notes = new Notes();
