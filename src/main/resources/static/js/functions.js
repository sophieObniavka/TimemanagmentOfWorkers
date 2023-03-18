function callFunctionByName(name, ...params) {
    const functionReference = this[name];
    if (isFunction(functionReference)) {
        functionReference(...params);
    }
}

function unCapitalize([first, ...rest]) {
    return first === undefined ? '' : first.toLowerCase() + rest.join('');
}

function combine(...data) {
    let combinedData = {};
    data.forEach(element => $.extend(true, combinedData, combinedData, element));
    return combinedData;
}

function getAllFormData(form) {
    let data = {};
    for (const selector of form) {
        data = combine(data, getFormData(selector));
    }
    return data;
}

function getData(element, prefix) {
    let result = {};
    const data = element.data();
    const length = prefix.length;
    if (data) {
        for (const key in data) {
            if (key.startsWith(prefix) && key.length > length) {
                let suffix = key.substring(length);
                result[unCapitalize(suffix)] = parse(data[key]);
            }
        }
    }
    return result;
}

function getElement(element, selector) {
    return getJQuery(element, selector)[0];
}

function getFormData(selector) {
    const form = $(selector)[0];
    if (form) {
        return Object.fromEntries(new FormData(form).entries());
    }
    return {};
}

function getJQuery(element, selector) {
    if (!element) {
        return $(selector);
    } else if (!(element instanceof jQuery)) {
        element = $(element);
    }
    return element.find(selector);
}

function getValue(element, selector) {
    const selected = getElement(element, selector);
    return selected ? selected.value : null;
}

function hideModals() {
    $('.modal').each((_index, element) => toggleModal($(element), false));
}

function isFunction(functionReference) {
    return typeof functionReference === 'function';
}

function isString(string) {
    return typeof string === 'string';
}

function log(title, object) {
    console.log(title + ':');
    console.log(object);
}

function onEvent(event) {
    let element = $(event.target);
    const parent = element.parent('tr');
    if (parent.length) {
        element = parent;
    }
    if (this != element[0]) {
        return;
    }
    let functionName = element.data('ts-function');
    functionName = functionName ? functionName : element.data('function');
    let functionParams = combine(getData(element, 'tsParam'), parse(element.data('ts-param')));
    functionParams['data'] = combine(functionParams.data, getData(element, 'tsVar'));
    functionParams['element'] = element;
    callFunctionByName(functionName, functionParams);
    event.stopPropagation();
}

function parse(data) {
    if (data) {
        let result = '' + data;
        if (!startsWith(result, "'", '{', '[')) {
            result = "'" + result + "'";
        }
        return $.parseJSON(result.replace(/'/g, '"'));
    }
    return {};
}

function printSheet() {
    window.print();
}

function replace(data, selector) {
    const content = $(data).find(selector).addBack(selector)[0];
    if (content) {
        $(selector).replaceWith(content);
        $(selector).trigger('ready');
        return true;
    }
    return false;
}

function replacePathVariables(url, data) {
    const matches = url.matchAll('{([^}]+)}');
    let index = 0;
    let result = '';
    for (const match of matches) {
        const prefix = url.substring(index, match.index);
        const replacement = data[match[1]];
        result = result + prefix + replacement;
        index = match.index + match[0].length;
    }
    return result + url.substring(index);
}

function request(params) {
    params['data'] = combine(params.data, getAllFormData(params.form));
    log('params', params);
    $.ajax({
        type: params.method,
        url: replacePathVariables(params.url, params.data),
        data: params.data,
        cache: false,
        success: function (response, _status, _xhr) {
            for (const selector in params.target) {
                if (replace(response, selector)) {
                    for (const callback of params.target[selector]) {
                        callFunctionByName(callback, params);
                    }
                    break;
                }
            }
        }
    });
}

function showModal(params) {
    hideModals();
    for (const selector in params.target) {
        const modal = $(selector).closest('.modal');
        toggleModal(modal, true);
    }
}

function showToast(params) {
    $(params.toast).find('.toast-detail').text(params.toastDetail);
    new bootstrap.Toast($(params.toast)).show();
}

function startsWith(string, ...prefixes) {
    let value = '' + string;
    let result = false;
    prefixes.forEach(prefix => {
        if (value.startsWith(prefix)) {
            result = true;
            return false;
        }
    })
    return result;
}

function toggleModal(modal, toggle) {
    if (modal) {
        modal.modal(toggle ? 'show' : 'hide');
    }
}

function toMs(time) {
    return new Date('2000-01-01T' + time).getTime();
}

function loadScript(filename) {
    $.getScript(filename);
}

loadScript('/javascript/' + window.location.href.split('/').pop() + ".js");