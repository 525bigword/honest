;(function ($, window, document, undefined) {
    'use strict';
    function Paging(element, options) {
        this.element = element;
        this.options = {
            pageNum: options.pageNum || 1, // 当前页码
            totalNum: options.totalNum, // 总页码
            totalList: options.totalList, // 数据总记录
            callback: options.callback // 回调函数
        };
        this.init();
    }
    Paging.prototype = {
        constructor: Paging,
        init: function () {
            this.createHtml();
            this.bindEvent();
        },
        createHtml: function () {
            var me = this;
            var content = [];
            var pageNum = me.options.pageNum;
            var totalNum = me.options.totalNum;
            var totalList = me.options.totalList;
            content.push("<button type='button' id='firstPage'>首页</button><button type='button' id='prePage'>上一页</button>");
            content.push("<button type='button' id='lastPage'>尾页</button><button type='button' id='nextPage'>下一页</button>");
            content.push("<span class='totalNum'> 共 " + totalNum + " 页 </span>");
            content.push("<span class='totalList'> 共 " + totalList + " 条记录 </span>");
            me.element.html(content.join(''));

            // DOM重新生成后每次调用是否禁用button
            setTimeout(function () {
                me.dis();
            }, 20);
        },
        bindEvent: function () {
            var me = this;
            me.element.off('click', 'button');
            // 委托新生成的dom监听事件
            me.element.on('click', 'button', function () {
                var id = $(this).attr('id');
                var num = parseInt($(this).html());
                var pageNum = me.options.pageNum;
                if (id === 'prePage') {
                    if (pageNum !== 1) {
                        me.options.pageNum -= 1;
                    }
                } else if (id === 'nextPage') {
                    if (pageNum !== me.options.totalNum) {
                        me.options.pageNum += 1;
                    }
                } else if (id === 'firstPage') {
                    if (pageNum !== 1) {
                        me.options.pageNum = 1;
                    }
                } else if (id === 'lastPage') {
                    if (pageNum !== me.options.totalNum) {
                        me.options.pageNum = me.options.totalNum;
                    }
                } else {
                    me.options.pageNum = num;
                }
                me.createHtml();
                if (me.options.callback) {
                    me.options.callback(me.options.pageNum);
                }
            });
        },
        dis: function () {
            var me = this;
            var pageNum = me.options.pageNum;
            var totalNum = me.options.totalNum;
            if (pageNum === 1) {
                me.element.children('#firstPage, #prePage').prop('disabled', true);
            } else if (pageNum === totalNum) {
                me.element.children('#lastPage, #nextPage').prop('disabled', true);
            }
        }
    };
    $.fn.paging = function (options) {
        return new Paging($(this), options);
    }
})(jQuery, window, document);