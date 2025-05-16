class Notes {

    out() { return this.pitch;};

    plotpoints() {
        var pp = statejs["frequencies"];

        let straight = (y) => (y - 23.5) * (440/7);
        let sp = pp.map((tu, i) => [tu[0], straight(i)]);

        sp.map((d, i) => {
            statejs['bnotes1'].create('point', [d[1], i],
                                      {name:d[0], color: "black"});
        });


        var violines = [30, 32, 34, 36, 38];
        violines.map((y) =>
            statejs['bnotes1'].create('line',[[150, y],
                                              [920, y]],
                                      {straightFirst:false,
                                       straightLast:false,
                                       strokeWidth:1,
                                       strokeColor:'black'}));


        var drawstiel = (shift, len) => (i) =>
            statejs['bnotes1']
            .create('line',[[sp[i][1]+shift, i],
                            [sp[i][1]+shift, i+len]],
                    {name: "stl" + sp[i][0],
                     straightFirst:false,
                     straightLast:false,
                     strokeWidth:4,
                     strokeColor:'black'});

        statejs["stielunten"] = [34, 35, 36, 37, 38, 39];
        statejs["stielunten"].map(drawstiel(-9, -7));
        statejs["stieloben"] = [33, 32, 31, 30, 29];
        statejs["stieloben"].map(drawstiel(9, 7));

        var setAtt = (att) => (idx) =>
            statejs.bnotes1.select(statejs["frequencies"][idx][0]).setAttribute(att);

        violines.map(setAtt({size: 9}));
        var viogaps = [28, 29, 31, 33, 35, 37, 39, 40];
        viogaps.map(setAtt({size: 9}));

        statejs['bnotes1'].create('image',["https://upload.wikimedia.org/wikipedia/commons/f/ff/GClef.svg", [170,26.4], [80,15.5]]);
    }

    anim1() {
        var rm = (d) =>
            d.reverse().map((i) => statejs['bnotes1']
                            .removeObject("stl" + statejs["frequencies"][i][0]));

        rm(statejs["stielunten"]);
        rm(statejs["stieloben"]);

        statejs["frequencies"].map((tu, i) => statejs['bnotes1'].select(tu[0]).moveTo([tu[1], i], 1500));

    }

    anim2() {
        statejs["frequencies"].map((tu, i) => statejs['bnotes1'].select(tu[0]).setAttribute({size: 2}).setLabel(""));

        statejs['bnotes1'].create('functiongraph', ['(7 * (log(x) - log(16.35)))/log(2)'], {strokeColor:'red'});
    }

    main(divid) {
        statejs['bnotes1'] = JXG.JSXGraph.initBoard(divid, {
            boundingbox: [120, 45, 950, 20],
            showCopyright:false,
            axis: false,
            grid: false
        });

        this.plotpoints();
    }
};

statejs["notes"] = new Notes();
