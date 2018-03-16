define(function (require, exports, module) {
    require('../../lib/plupload/plupload.full.min.js');
    var layer = require('../../lib/layer/2.4/layer.js');
    exports.init = function (options) {
        var dialog;
        var config = {
            runtimes: 'html5,flash,html4',
            browse_button: '',
            container: '',
            max_file_size: '20mb',
            //multi_selection: false,
            //url: 'upload.php',
            url: '',
            flash_swf_url: require.resolve('./') + 'Moxie.swf',
            filters: {
                mime_types: [
                    {title: "Image files", extensions: "jpg,gif,png,bmp"},
                    {title: "Doc files", extensions: "doc,docx,xls,xlsx,pdf"}
                ],
                max_file_size: "2mb",
                prevent_duplicates: true
            }
            /*filters: [{
             title: "图像文件",
             extensions: "jpg,gif,png,bmp"
             }, {
             title: "Doc files",
             extensions: "doc,docx,xls,xlsx,pdf"
             }],*/

        };
        /**
         * "HTTP Error."
         * "N/A"
         * "Init error."
         * "File size error."
         * "File extension error."
         * "Security error."
         * "Generic error."
         * "IO error."
         * "Image error."
         */
        $.extend(config, options);
        if (options.mime_types && options.mime_types == 'img') {
            config.filters.mime_types = [
                {title: "Image files", extensions: "jpg,gif,png,bmp,jpeg"}
            ]
        }
        if (options.mime_types && options.mime_types == 'doc') {
            config.filters.mime_types = [
                {title: "Doc files", extensions: "doc,docx,xls,xlsx,pdf"}
            ]
        }
        // Chinese (China) (zh_CN)
        plupload.addI18n({
            "Stop Upload": "停止上传",
            "Upload URL might be wrong or doesn't exist.": "上传的URL可能是错误的或不存在",
            "tb": "tb",
            "Size": "大小",
            "Close": "关闭",
            "You must specify either browse_button or drop_element.": "您必须指定 browse_button 或者 drop_element",
            "Init error.": "初始化错误。",
            "Add files to the upload queue and click the start button.": "将文件添加到上传队列，然后点击”开始上传“按钮",
            "List": "列表",
            "Filename": "文件名",
            "%s specified, but cannot be found.": "%s 已指定，但是没有找到",
            "Image format either wrong or not supported.": "图片格式错误或者不支持",
            "Status": "状态",
            "HTTP Error.": "HTTP 错误",
            "Start Upload": "开始上传",
            "Error: File too large:": "错误: 文件太大:",
            "kb": "kb",
            "Duplicate file error.": "重复文件错误",
            "File size error.": "请上传50M以内的文件",
            "N/A": "N/A",
            "gb": "gb",
            "Error: Invalid file extension:": "错误：无效的文件扩展名:",
            "Select files": "选择文件",
            "%s already present in the queue.": "%s 已经在当前队列里。",
            "Resoultion out of boundaries! <b>%s</b> runtime supports images only up to %wx%hpx.": "超限。<b>%s</b> 支持最大 %wx%hpx 的图片。",
            "File: %s": "文件: %s",
            "b": "b",
            "Uploaded %d/%d files": "已上传 %d/%d 个文件",
            "Upload element accepts only %d file(s) at a time. Extra files were stripped.": "每次只接受同时上传 %d 个文件，多余的文件将会被删除",
            "%d files queued": "%d 个文件加入到队列",
            "File: %s, size: %d, max file size: %d": "文件: %s, 大小: %d, 最大文件大小: %d",
            "Thumbnails": "缩略图",
            "Drag files here.": "把文件拖到这里",
            "Runtime ran out of available memory.": "运行时已消耗所有可用内存",
            "File count error.": "文件数量错误",
            "File extension error.": "请上传50M以内的文件",
            "mb": "mb",
            "Add Files": "增加文件"
        });
        //dialog
        //trigger order: init -> Error $ ro FilesAdded -> BeforeUpload -> QueueChanged? -> UploadProgress -> FileUploaded
        var uploader = new plupload.Uploader(config);
        uploader.bind('Init', function (up, opt) {
            if (config.FilesAdded) config.FilesAdded(up, opt);
        });
        /*  var dialog = bootbox.dialog({
         title: '正在上传...',
         closeButton: false,
         size: 'small',
         show: false,
         message: "上传中..."
         });*/

        uploader.init();
        var trueFileAdded = function (up, files) {
            var message = '';
            $.each(files, function (i, file) {
                message += '<div id="' + file.id + '" class="progress" style="margin: 10px 15px;"><div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em; width: 2%;">0%</div> </div>';//<div id="' + file.id + '" class="progress ' + (file.message ? ' upload-error' : '') + '"><div class="progress-bar progress-bar-success" data-transitiongoal="100"></div></div>';
            });
            dialog = layer.open({
                type: 1,
                title: '上传进度',
                skin: 'layui-layer-demo', //样式类名
                closeBtn: 1, //不显示关闭按钮
                anim: 2,
                area: ['450px'],
                shade: false, //开启遮罩关闭
                content: message
            });

            if (config.FilesAdded) config.FilesAdded(up, files);
            up.refresh();
            uploader.start();
        }
        uploader.bind('FilesAdded', function (up, files) {
            if (config.FilesAdded) {
                config.FilesAdded(up, files);
                trueFileAdded(up, files);
            } else {
                trueFileAdded(up, files);
            }
        });

        uploader.bind('QueueChanged', function (up) {
            //console.log('QueueChanged: ' + uploader.files.length);
            if (config.QueueChanged) config.QueueChanged(up);
        });

        uploader.bind('BeforeUpload', function (up, file) {
            if (config.BeforeUpload) config.BeforeUpload(up, file);
            //console.log('BeforeUpload' + uploader.files.length);
        });

        uploader.bind('UploadProgress', function (up, file) {
            //console.log('UploadProgress' + uploader.files.length);
            // $('.progress .progress-bar').progressbar({display_text: 'fill'});
            $('#' + file.id + " .progress-bar").width(file.percent + "%");
            $('#' + file.id + " .progress-bar").html(file.percent + "%");
        });

        uploader.bind('Error', function (up, err) {
        	debugger;
        	alert("文件上传错误:"+err.message)
            layer.title('上传结果', dialog);
            if (err.file) {
                err.file.message = err.message;
                $('#' + err.file.id + " .progress-bar").addClass('progress-bar-danger');
                $('#' + err.file.id + " .progress-bar").html("上传失败," + err.message);
                // dialog.setClosable(true);
            }

//            layer.msg(err.message, {
//  			  time: 1000 // 1秒关闭（如果不配置，默认是3秒）
//  			});
            up.refresh(); // Reposition Flash/Silverlight
        });
        uploader.bind('FileUploaded', function (up, file, data) {
            var result = JSON.parse(data.response);
            layer.title('上传结果', dialog);
            if (result.status != 0) {
                $('#' + file.id + " .progress-bar").addClass('progress-bar-danger');
                $('#' + file.id + " .progress-bar").html("上传失败!");
                // dialog.setClosable(true);
                // $('.bootbox .modal-header').prepend('<button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button>');
            } else {
                $('#' + file.id + " .progress-bar").html("上传成功!");
                // setTimeout(layer.close(dialog), 1000)
            }
            if (config.FileUploaded) {
                if (up.total.queued == 0) {
                }
                config.FileUploaded(up, file, data);
            }
            up.removeFile(file);
        });
        uploader.bind('UploadComplete', function (up, file) {
            setTimeout(function(){layer.close(dialog)},2000)
            //TODO
        });

        return uploader;
    }
});
