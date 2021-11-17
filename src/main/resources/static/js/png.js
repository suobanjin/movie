﻿var DD_belatedPNG = {
    ns: "DD_belatedPNG", imgSize: {}, delay: 10, nodesFixed: 0, createVmlNameSpace: function () {
        if (document.namespaces && !document.namespaces[this.ns]) {
            document.namespaces.add(this.ns, "urn:schemas-microsoft-com:vml")
        }
    }, createVmlStyleSheet: function () {
        var d, c;
        d = document.createElement("style");
        d.setAttribute("media", "screen");
        document.documentElement.firstChild.insertBefore(d, document.documentElement.firstChild.firstChild);
        if (d.styleSheet) {
            d = d.styleSheet;
            d.addRule(this.ns + "\\:*", "{behavior:url(#default#VML)}");
            d.addRule(this.ns + "\\:shape", "position:absolute;");
            d.addRule("img." + this.ns + "_sizeFinder", "behavior:none; border:none; position:absolute; z-index:-1; top:-10000px; visibility:hidden;");
            this.screenStyleSheet = d;
            c = document.createElement("style");
            c.setAttribute("media", "print");
            document.documentElement.firstChild.insertBefore(c, document.documentElement.firstChild.firstChild);
            c = c.styleSheet;
            c.addRule(this.ns + "\\:*", "{display: none !important;}");
            c.addRule("img." + this.ns + "_sizeFinder", "{display: none !important;}")
        }
    }, readPropertyChange: function () {
        var e, f, d;
        e = event.srcElement;
        if (!e.vmlInitiated) {
            return
        }
        if (event.propertyName.search("background") !== -1 || event.propertyName.search("border") !== -1) {
            DD_belatedPNG.applyVML(e)
        }
        if (event.propertyName === "style.display") {
            f = (e.currentStyle.display === "none") ? "none" : "block";
            for (d in e.vml) {
                if (e.vml.hasOwnProperty(d)) {
                    e.vml[d].shape.style.display = f
                }
            }
        }
        if (event.propertyName.search("filter") !== -1) {
            DD_belatedPNG.vmlOpacity(e)
        }
    }, vmlOpacity: function (d) {
        if (d.currentStyle.filter.search("lpha") !== -1) {
            var c = d.currentStyle.filter;
            c = parseInt(c.substring(c.lastIndexOf("=") + 1, c.lastIndexOf(")")), 10) / 100;
            d.vml.color.shape.style.filter = d.currentStyle.filter;
            d.vml.image.fill.opacity = c
        }
    }, handlePseudoHover: function (b) {
        setTimeout(function () {
            DD_belatedPNG.applyVML(b)
        }, 1)
    }, fix: function (d) {
        if (this.screenStyleSheet) {
            var f, e;
            f = d.split(",");
            for (e = 0; e < f.length; e++) {
                this.screenStyleSheet.addRule(f[e], "behavior:expression(DD_belatedPNG.fixPng(this))")
            }
        }
    }, applyVML: function (b) {
        b.runtimeStyle.cssText = "";
        this.vmlFill(b);
        this.vmlOffsets(b);
        this.vmlOpacity(b);
        if (b.isImg) {
            this.copyImageBorders(b)
        }
    }, attachHandlers: function (n) {
        var j, h, m, k, a, l;
        j = this;
        h = {resize: "vmlOffsets", move: "vmlOffsets"};
        if (n.nodeName === "A") {
            k = {
                mouseleave: "handlePseudoHover",
                mouseenter: "handlePseudoHover",
                focus: "handlePseudoHover",
                blur: "handlePseudoHover"
            };
            for (a in k) {
                if (k.hasOwnProperty(a)) {
                    h[a] = k[a]
                }
            }
        }
        for (l in h) {
            if (h.hasOwnProperty(l)) {
                m = function () {
                    j[h[l]](n)
                };
                n.attachEvent("on" + l, m)
            }
        }
        n.attachEvent("onpropertychange", this.readPropertyChange)
    }, giveLayout: function (b) {
        b.style.zoom = 1;
        if (b.currentStyle.position === "static") {
            b.style.position = "relative"
        }
    }, copyImageBorders: function (e) {
        var f, d;
        f = {borderStyle: true, borderWidth: true, borderColor: true};
        for (d in f) {
            if (f.hasOwnProperty(d)) {
                e.vml.color.shape.style[d] = e.currentStyle[d]
            }
        }
    }, vmlFill: function (l) {
        if (!l.currentStyle) {
            return
        } else {
            var k, m, n, i, h, j;
            k = l.currentStyle
        }
        for (i in l.vml) {
            if (l.vml.hasOwnProperty(i)) {
                l.vml[i].shape.style.zIndex = k.zIndex
            }
        }
        l.runtimeStyle.backgroundColor = "";
        l.runtimeStyle.backgroundImage = "";
        m = true;
        if (k.backgroundImage !== "none" || l.isImg) {
            if (!l.isImg) {
                l.vmlBg = k.backgroundImage;
                l.vmlBg = l.vmlBg.substr(5, l.vmlBg.lastIndexOf('")') - 5)
            } else {
                l.vmlBg = l.src
            }
            n = this;
            if (!n.imgSize[l.vmlBg]) {
                h = document.createElement("img");
                n.imgSize[l.vmlBg] = h;
                h.className = n.ns + "_sizeFinder";
                h.runtimeStyle.cssText = "behavior:none; position:absolute; left:-10000px; top:-10000px; border:none; margin:0; padding:0;";
                j = function () {
                    this.width = this.offsetWidth;
                    this.height = this.offsetHeight;
                    n.vmlOffsets(l)
                };
                h.attachEvent("onload", j);
                h.src = l.vmlBg;
                h.removeAttribute("width");
                h.removeAttribute("height");
                document.body.insertBefore(h, document.body.firstChild)
            }
            l.vml.image.fill.src = l.vmlBg;
            m = false
        }
        l.vml.image.fill.on = !m;
        l.vml.image.fill.color = "none";
        l.vml.color.shape.style.backgroundColor = k.backgroundColor;
        l.runtimeStyle.backgroundImage = "none";
        l.runtimeStyle.backgroundColor = "transparent"
    }, vmlOffsets: function (c) {
        var s, y, b, o, q, x, p, w, u, t, v;
        s = c.currentStyle;
        y = {
            W: c.clientWidth + 1,
            H: c.clientHeight + 1,
            w: this.imgSize[c.vmlBg].width,
            h: this.imgSize[c.vmlBg].height,
            L: c.offsetLeft,
            T: c.offsetTop,
            bLW: c.clientLeft,
            bTW: c.clientTop
        };
        b = (y.L + y.bLW == 1) ? 1 : 0;
        o = function (a, e, f, d, g, h) {
            a.coordsize = d + "," + g;
            a.coordorigin = h + "," + h;
            a.path = "m0,0l" + d + ",0l" + d + "," + g + "l0," + g + " xe";
            a.style.width = d + "px";
            a.style.height = g + "px";
            a.style.left = e + "px";
            a.style.top = f + "px"
        };
        o(c.vml.color.shape, (y.L + (c.isImg ? 0 : y.bLW)), (y.T + (c.isImg ? 0 : y.bTW)), (y.W - 1), (y.H - 1), 0);
        o(c.vml.image.shape, (y.L + y.bLW), (y.T + y.bTW), (y.W), (y.H), 1);
        q = {X: 0, Y: 0};
        if (c.isImg) {
            q.X = parseInt(s.paddingLeft, 10) + 1;
            q.Y = parseInt(s.paddingTop, 10) + 1
        } else {
            for (u in q) {
                if (q.hasOwnProperty(u)) {
                    this.figurePercentage(q, y, u, s["backgroundPosition" + u])
                }
            }
        }
        c.vml.image.fill.position = (q.X / y.W) + "," + (q.Y / y.H);
        x = s.backgroundRepeat;
        p = {T: 1, R: y.W + b, B: y.H, L: 1 + b};
        w = {X: {b1: "L", b2: "R", d: "W"}, Y: {b1: "T", b2: "B", d: "H"}};
        if (x !== "repeat" || c.isImg) {
            t = {T: (q.Y), R: (q.X + y.w), B: (q.Y + y.h), L: (q.X)};
            if (x.search("repeat-") != -1) {
                v = x.split("repeat-")[1].toUpperCase();
                t[w[v].b1] = 1;
                t[w[v].b2] = y[w[v].d]
            }
            if (t.B > y.H) {
                t.B = y.H
            }
            c.vml.image.shape.style.clip = "rect(" + t.T + "px " + (t.R + b) + "px " + t.B + "px " + (t.L + b) + "px)"
        } else {
            c.vml.image.shape.style.clip = "rect(" + p.T + "px " + p.R + "px " + p.B + "px " + p.L + "px)"
        }
    }, figurePercentage: function (j, i, l, g) {
        var h, k;
        k = true;
        h = (l == "X");
        switch (g) {
            case"left":
            case"top":
                j[l] = 0;
                break;
            case"center":
                j[l] = 0.5;
                break;
            case"right":
            case"bottom":
                j[l] = 1;
                break;
            default:
                if (g.search("%") != -1) {
                    j[l] = parseInt(g, 10) / 100
                } else {
                    k = false
                }
        }
        j[l] = Math.ceil(k ? ((i[h ? "W" : "H"] * j[l]) - (i[h ? "w" : "h"] * j[l])) : parseInt(g, 10));
        if (j[l] % 2 === 0) {
            j[l]++
        }
        return j[l]
    }, fixPng: function (i) {
        i.style.behavior = "none";
        var l, h, k, e, j;
        if (i.nodeName === "BODY" || i.nodeName === "TD" || i.nodeName === "TR") {
            return
        }
        i.isImg = false;
        if (i.nodeName === "IMG") {
            if (i.src.toLowerCase().search(/\.png$/) != -1) {
                i.isImg = true;
                i.style.visibility = "hidden"
            } else {
                return
            }
        } else {
            if (i.currentStyle.backgroundImage.toLowerCase().search(".png") === -1) {
                return
            }
        }
        l = DD_belatedPNG;
        i.vml = {color: {}, image: {}};
        h = {shape: {}, fill: {}};
        for (e in i.vml) {
            if (i.vml.hasOwnProperty(e)) {
                for (j in h) {
                    if (h.hasOwnProperty(j)) {
                        k = l.ns + ":" + j;
                        i.vml[e][j] = document.createElement(k)
                    }
                }
                i.vml[e].shape.stroked = false;
                i.vml[e].shape.appendChild(i.vml[e].fill);
                i.parentNode.insertBefore(i.vml[e].shape, i)
            }
        }
        i.vml.image.shape.fillcolor = "none";
        i.vml.image.fill.type = "tile";
        i.vml.color.fill.on = false;
        l.attachHandlers(i);
        l.giveLayout(i);
        l.giveLayout(i.offsetParent);
        i.vmlInitiated = true;
        l.applyVML(i)
    }
};
try {
    document.execCommand("BackgroundImageCache", false, true)
} catch (r) {
}
DD_belatedPNG.createVmlNameSpace();
DD_belatedPNG.createVmlStyleSheet();