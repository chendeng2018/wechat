$(function() {
    $('#dateTimeRange').daterangepicker({
        buttonClasses: ['btn btn-default'],
        applyClass: 'btn-small btn-primary blue',
        cancelClass: 'btn-small',
        locale: {
            format: 'YYYY-MM-DD HH:mm:ss',
            applyLabel: '确认',
            cancelLabel: '取消',
            fromLabel : '起始时间',
            toLabel : '结束时间',
            customRangeLabel : '自定义',
            daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月'
            ],
            firstDay : 1
        },
        ranges : {
            //'最近1小时': [moment().subtract('hours',1), moment()],
            '今日': [moment().startOf('day'), moment()],
            '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
            '最近7日': [moment().subtract('days', 6), moment()],
            '最近30日': [moment().subtract('days', 29), moment()],
            '本月': [moment().startOf("month"),moment().endOf("month")],
            '上个月': [moment().subtract(1,"month").startOf("month"),moment().subtract(1,"month").endOf("month")]
        },
        opens : 'center',    // 日期选择框的弹出位置
        separator : ' 至 ',
        showWeekNumbers : true,     // 是否显示第几周
        //timePicker: true,
        //timePickerIncrement : 10, // 时间的增量，单位为分钟
        //timePicker12Hour : false, // 是否使用12小时制来显示时间
        //maxDate : moment(),           // 最大时间
        format: 'YYYY-MM-DD HH:mm:ss'

    },
        function(start, end, label) { // 格式化日期显示框
        $('#beginTime').val(start.format('YYYY-MM-DD HH:mm:ss'));
        $('#endTime').val(end.format('YYYY-MM-DD HH:mm:ss'));
    })
        .next().on('click', function(){
        $(this).prev().focus();
    });
    $('#dateTimeRange').val(moment().subtract(29, 'days').format('YYYY-MM-DD HH:mm:ss') + ' - ' + moment().format('YYYY-MM-DD HH:mm:ss'));

});

$('#dateTimeRange').on('apply.daterangepicker', function(ev, picker) {
	$('#beginTime').val(picker.startDate.format('YYYY-MM-DD'));
	$('#endTime').val(picker.endDate.format('YYYY-MM-DD'));
});
/**
 * 清除时间
 */
function begin_end_time_clear() {
    $('#dateTimeRange').val('');
    $('#taskname').val('');
    $('#beginTime').val('');
    $('#endTime').val('');
}
function begin_end_time_clearPingjia() {
    $('#dateTimeRange2').val('');
    $('#tasknamePingjia').val('');
    $('#beginTimePingjia').val('');
    $('#endTimePingjia').val('');
}

$(function() {
    $('#dateTimeRange2').daterangepicker({
            buttonClasses: ['btn btn-default'],
            applyClass: 'btn-small btn-primary blue',
            cancelClass: 'btn-small',
            locale: {
                format: 'YYYY-MM-DD HH:mm:ss',
                applyLabel: '确认',
                cancelLabel: '取消',
                fromLabel : '起始时间',
                toLabel : '结束时间',
                customRangeLabel : '自定义',
                daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                    '七月', '八月', '九月', '十月', '十一月', '十二月'
                ],
                firstDay : 1
            },
            ranges : {
                //'最近1小时': [moment().subtract('hours',1), moment()],
                '今日': [moment().startOf('day'), moment()],
                '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
                '最近7日': [moment().subtract('days', 6), moment()],
                '最近30日': [moment().subtract('days', 29), moment()],
                '本月': [moment().startOf("month"),moment().endOf("month")],
                '上个月': [moment().subtract(1,"month").startOf("month"),moment().subtract(1,"month").endOf("month")]
            },
            opens : 'center',    // 日期选择框的弹出位置
            separator : ' 至 ',
            showWeekNumbers : true,     // 是否显示第几周
            //timePicker: true,
            //timePickerIncrement : 10, // 时间的增量，单位为分钟
            //timePicker12Hour : false, // 是否使用12小时制来显示时间
            //maxDate : moment(),           // 最大时间
            format: 'YYYY-MM-DD HH:mm:ss'

        },
        function(start, end, label) { // 格式化日期显示框
            $('#beginTimePingjia').val(start.format('YYYY-MM-DD HH:mm:ss'));
            $('#endTimePingjia').val(end.format('YYYY-MM-DD HH:mm:ss'));
        })
        .next().on('click', function(){
        $(this).prev().focus();
    });
    $('#dateTimeRange2').val(moment().subtract(29, 'days').format('YYYY-MM-DD HH:mm:ss') + ' - ' + moment().format('YYYY-MM-DD HH:mm:ss'));

});