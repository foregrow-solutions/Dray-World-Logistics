(function (context, api) {
    "use strict";
    if (typeof define === "function" && define.amd) {
        define(function (require, exports, module) {
            var jQuery = require('jquery');
            jQuery.noConflict();
            return context.api = api(jQuery);
        });
    } else {
        alert('Include Require JS.');
    }
}(this, function ($) {
    "use strict";

    var api = {
        version: "1.0.0"
    };
    api.post = function (url, data, callback, complete, headers) {
        if (!headers) {
            headers = {}
        }
        $.ajax({
            url: url,
            data: JSON.stringify(data),
            headers: {
                headers,
                Authorization: 'Bearer ' + localStorage.getItem('token')
            },
            type: "POST",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: callback,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 401 || XMLHttpRequest.status == 403) {
                    api.logout();
                }
            },
            complete: complete
        });
    }

    api.put = function(url, data, callback, complete, headers) {
        if(!headers) {
            headers = {}
        }
        $.ajax({
            url: url,
            headers:{
                headers,
                Authorization: 'Bearer ' + localStorage.getItem('token')
            },
            type: 'PUT',
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: "application/json;charset=utf-8",
            success: callback,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 401 || XMLHttpRequest.status == 403) {
                    api.logout();
                }
            },
            complete: complete
        });
    }

    api.get = function (url, callback, complete, headers) {
        if (!headers) {
            headers = {}
        }
        $.ajax({
            url: url,
            contentType: false,
            processData: false,
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
                headers,
            },
            type: 'GET',
            success: callback,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 401 || XMLHttpRequest.status == 403) {
                    api.logout();
                }
            },
            complete: complete
        });
    }
    api.delete = function (url, callback, complete, headers) {
        if(!headers) {
            headers = {};
        }
        $.ajax({
            url: url,
            type: 'DELETE',
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
                headers,
            },
            success: callback
        })
    }

}));