function $$(id) {
    if (document.getElementById) {
        return document.getElementById(id);
    } else if (document.layers) {
        return document.layers[id];
    } else {
        return false;
    }
} (function() {
    function initHead() {
        setTimeout(showSubSearch, 0)
    };
    function showSubSearch() {
        $$("pt1").onmouseover = function() {
            $$("pt2").style.display = "";
            $$("pt1").className = "select select_hover"
        };
        $$("pt1").onmouseout = function() {
            $$("pt2").style.display = "none";
            $$("pt1").className = "select"
        };
    };
    initHead();
})();

