define("javascripts/util/string", ["require", "exports"], function (require, exports) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    exports.upperCase = void 0;
    function upperCase(str) {
        return str.toUpperCase();
    }
    exports.upperCase = upperCase;
});
define("javascripts/util/logger", ["require", "exports"], function (require, exports) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    exports.log = void 0;
    function log(str) {
        console.log(str);
    }
    exports.log = log;
});
define("javascripts/main", ["require", "exports", "javascripts/util/string", "javascripts/util/logger"], function (require, exports, string_1, logger_1) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    // Legacy is a "global" object which typescript will complain about without the typings file
    logger_1.log(Legacy.someFunc(string_1.upperCase("Hello World")));
});
//# sourceMappingURL=app.js.map
