/**
 * Created by Chenny on 2016/9/24.
 */
/*针对表单对象扩展一个用于定制ajax请求的方法*/
/*需要前置插件bootstrap-validator-master*/
$.fn.extend({
    valid: function (a,b) {
        $(this).validator().on('submit',{failed:b,passed:a}, function (e) {
            if (e.isDefaultPrevented()) {//表单验证不通过时调用a
                if($.isFunction(e.data.failed)){
                    e.data.failed()
                }
            } else {//表单验证通过时调用b
                if($.isFunction(e.data.passed)){
                    e.data.passed()
                }
                e.preventDefault()
            }
        })
    }
})