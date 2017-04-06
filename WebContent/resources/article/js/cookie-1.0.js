(function(b) {
    var c = {};
    var a = decodeURIComponent;
    var f = encodeURIComponent;
    c.get = function(k, j) {
        i(k);
        if (typeof j === "function") {
            j = {
                converter: j
            }
        } else {
            j = j || {}
        }
        var l = e(document.cookie, !j.raw);
        return (j.converter || g)(l[k])
    };
    c.set = function(m, o, l) {
        i(m);
        l = l || {};
        var j = l.expires;
        var n = l.domain;
        var p = l.path;
        if (!l.raw) {
            o = f(String(o))
        }
        var q = m + "=" + o;
        var k = j;
        if (typeof k === "number") {
            k = new Date();
            k.setDate(k.getDate() + j)
        }
        if (k instanceof Date) {
            q += "; expires=" + k.toUTCString()
        }
        if (h(n)) {
            q += "; domain=" + n
        }
        if (h(p)) {
            q += "; path=" + p
        }
        if (l.secure) {
            q += "; secure"
        }
        document.cookie = q;
        return q
    };
    c.remove = function(k, j) {
        j = j || {};
        j.expires = new Date(0);
        return this.set(k, "", j)
    };
    function e(r, t) {
        var s = {};
        if (d(r) && r.length > 0) {
            var j = t ? a: g;
            var p = r.split(/;\s/g);
            var q;
            var k;
            var m;
            for (var l = 0,
            n = p.length; l < n; l++) {
                m = p[l].match(/([^=]+)=/i);
                if (m instanceof Array) {
                    try {
                        q = a(m[1]);
                        k = j(p[l].substring(m[1].length + 1))
                    } catch(o) {}
                } else {
                    q = a(p[l]);
                    k = ""
                }
                if (q) {
                    s[q] = k
                }
            }
        }
        return s
    }
    function d(j) {
        return typeof j === "string"
    }
    function h(j) {
        return d(j) && j !== ""
    }
    function i(j) {
        if (!h(j)) {
            throw new TypeError("Cookie name must be a non-empty string")
        }
    }
    function g(j) {
        return j
    }
    b.cookie = c
})(window);