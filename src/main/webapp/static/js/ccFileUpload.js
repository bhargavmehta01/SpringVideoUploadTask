/**
 * ccFileUpload - ConsistentCoder File Upload
 * 
 * License: MIT
 * Copyright: 2015 ConsistentCoder.com (http://consistentcoder.com)
 *
 * IMPORTANT ! ! !
 * IT IS A MUST TO DO A BACK-END (JAVA/PHP/etc) CHECKING FOR FILE TYPE RESTRICTIONS, TOO.
 * 
 * Tutorial Page: http://consistentcoder.com/create-a-file-uploader-using-jquery-and-bootstrap
 * GitHub Page: https://github.com/ConsistentCoder/ccFileUpload
 */
(function($){
    /**
     * Global vars.
     * Available in all methods.
     */
    var __options = [];
    var __counter = 0;
    
    $.fn.extend({
        ccFileUpload: function(options){
            var _this = this;
            
            /**
             * Default settings.
             */
            var defaults = {
                /**
                 * The input name, ex. name="file".
                 */
                name: 'file',
                
                /**
                 * The link where the upload will be sent,
                 * same with action="uploadUrl".
                 */
                uploadUrl: 'upload',
                
                /**
                 * Whether a synchronous(all at the same time)
                 * or not synchronous(one by one)
                 * upload will be performed.
                 */
                sync : true,
                
                /**
                 * The id of the element(div)
                 * where the previews/upload details will appear.
                 */
                previews: 'previews',
                
                /**
                 * The id of the element(div) where
                 * the server response will be printed.
                 * 
                 * Sample expected server response.
                 * {
                 *      "response":
                 *      [
                 *          "You have successfully uploaded image no. 1!",
                 *          "You have successfully uploaded image no. 2!"
                 *      ]
                 * }
                 */
                response: 'response',
                
                /**
                 * The id of the element where the
                 * total number of uploads will be displayed.
                 */
                counter: 'counter',
                
                /**
                 * The classes the will be added
                 * to the preview instance.
                 */
                columnClass: 'col-sm-3 text-center',
                
                /**
                 * The list of file extensions
                 * of the allowed files.
                 */
                allowedFiles: ['gif','png','jpg','jpeg'],
                
                /**
                 * The list of the files that
                 * will generate previews.
                 */
                allowedPreviews: ['gif','png','jpg','jpeg'],
                
                /**
                 * Set up animation speed options.
                 * Possible user input values
                 * slow | normal | fast
                 * where normal is the default.
                 */
                setAnimSpeed: {'slow':'ccAnimateSlow', 'normal':'ccAnimate', 'fast':'ccAnimateFast'},
            };
            
            /**
             * Set the default speed to normal
             * if the user given is not on the option list.
             */
            if(defaults.setAnimSpeed[options.animSpeed] == undefined){
                options.animSpeed = 'normal';
            }
            
            /**
             * Overwrite default settings
             * for any user set values.
             */
            __options = $.extend(defaults, options);
            
            /**
             * Create the initial file input field. 
             */
            var file = _this.createInput({type:'file', name:__options.name, cssClass:'hidden', multiple:true});
            
            /**
             * Create the button that will trigger the file field.
             */
            var fileDecoy = _this.createInput({type:'button', cssClass:'button btn btn-sm', text:'Choose File'});

            /**
             * Append the file input field
             * and bind the upload function to it.
             */
            _this.append(file.bind('change',function(){
                                    _this.upload($(this).get(0).files, __options.sync);
                                })
                        );
            
            /**
             * Append the 'decoy' button with a function
             * that will trigger the input field on click.
             */
            _this.append(fileDecoy.bind('click', function(e){
                e.preventDefault();
                file.trigger('click');
            }));
            
            /**
             * Create the initial message.
             */
            $('#'+__options.previews).html($('<div/>').addClass('text-center text-muted').append($('<span/>').addClass('glyphicon glyphicon-download').css({'font-size':'200px'}).prop('aria-hidden','true')));

            return _this;
        },
        
        /**
         * Function that will create an input field.
         * @param: options[type, name, cssClass, text, multiple].
         * type='Field Type'.
         * name='String, Given Field Name'.
         * cssClass='String, Given CSS Class'.
         * text='String, Given Field Value'.
         * multiple='Boolean, true=Field is multiple'.
         */
        createInput: function(options){
            var input = $('<input/>');
            if(options.multiple == true){
                input.prop('multiple',true);
            }
            return input.attr('type', options.type).attr('name', options.name).addClass(options.cssClass).val(options.text);
        },
        
        /**
         * Function that will show a preview of a chosen file
         * if it's within the allowed previewed file list,
         * else, only file name will be displayed.
         */
        showPreview: function(file, i){
            /**
             * Clear previews container on first file select.
             */
            if((__counter+i)==0){
                $('#'+__options.previews).empty();
            }
            
            /**
             * Create the preview container.
             */
            var prvwCntnr = $('<div/>').prop("id","prvwCntnr-"+i);
            
            if($.inArray(file.name.split('.').pop().toLowerCase(), __options.allowedPreviews) != -1){
                var reader = new FileReader();
                reader.onload = function(e){
                    $('<div/>').append($('<span/>').append(file.name)).append($('<img/>').attr('src', e.target.result).addClass('thumbnail img-responsive center-block').css({'max-height':'200px','max-width':'200px'})).prependTo(prvwCntnr);
                };
                reader.readAsDataURL(file);
            }
            else{
                $('<p/>').append(file.name).addClass('well well-sm').prependTo(prvwCntnr);
            }
            
            $('#'+__options.previews).prepend(
                    prvwCntnr.addClass(__options.columnClass)
                        .append($('<div/>').addClass('progress').append($('<div/>').addClass('progress-bar progress-bar-striped active').css({'width':'50%'}).attr('role', 'progressbar')))
                        .hide().removeClass('hidden').animate({opacity:"toggle", "top":"+10px"}, {duration:500, queue:false}).animate({"top":"-20"}, 100).animate({"top":"+10px"}, 100)
                   );
        },
        
        /**
         * Function that will show the updated
         * counter to the user.
         */
        updateCounter: function(){
            $('.'+__options.counter).html(__counter);
        },

        /**
         * Function that will send 
         * files to the server.
         */
        upload: function(files, sync){
            /**
             * Do nothing if files is empty.
             */
            if(!files.length){
                return;
            }
            
            /**
             * Create a container for valid files. 
             */
            var validFiles = [];
            
            /**
             * Get the file extension and check if it is allowed.
             * Add to validFiles list if true,
             * else display an error message.
             */
            for(var vf=0; vf<files.length; vf++){
                if($.inArray(files[vf].name.split('.').pop().toLowerCase(), __options.allowedFiles) != -1){
                    validFiles.push(files[vf]);
                }
                else{
                    $("#" + __options.response).prepend($('<p/>').addClass('text-danger').append("Error! " + files[vf].name + " is not allowed to be uploaded."));
                }
            }
            
            /**
             * Create a new FormData. 
             */
            var formData = new FormData();
            
            /**
             * Create a new container for temporary files.
             * Mainly used when sync = false.
             */
            var tempFiles = [];
            
            /**
             * Loop through the valid files.
             */
            for(var i=0; i<validFiles.length; i++){
                if(sync){ //all together
                    /**
                     * Append each valid file to formData.
                     */
                    formData.append(__options.name, validFiles[i]);
                    
                    /**
                     * Call the 'showPreview' function.
                     * @param: A validFiles object.
                     * @param: Auto generated id.
                     */
                    $().showPreview(validFiles[i], (__counter+i));
                }
                else{ //one by one
                    /**
                     * Store valid files to tempFiles.
                     */
                    tempFiles[i] = validFiles[i];
                }
            }
            
            if(!sync){ //one by one
                /**
                 * Append the first valid file to formData.
                 */
                formData.append(__options.name, tempFiles[0]);
                
                /**
                 * Call the 'showPreview' function.
                 * @param: A tempFiles object.
                 * @param: Auto generated id.
                 */
                $().showPreview(tempFiles[0], __counter);
            }
        
            $.ajax({
                url : __options.uploadUrl,
                type : 'POST',
                data : formData,
                enctype : 'multipart/form-data',
                processData : false,
                contentType : false,
                success : function(data){
                    if(sync){ //all together
                        $.each(files, function(key, file){
                            /**
                             * Show server response,
                             * and add a delete button.
                             */
                            $().showResponse($("#prvwCntnr-"+__counter), data.response[key]);
                            
                            /**
                             * Increment file counter.
                             */
                            __counter++;
                        });
                    }
                    else{ //one by one
                        /**
                         * Show server response,
                         * and add a delete button.
                         */
                        $().showResponse($("#prvwCntnr-"+__counter), data.response[0]);
                        
                        /**
                         * Increment file counter.
                         */
                        __counter++;
                    }
                }
            }).always(function(){
                /**
                 * Remove the file that has
                 * just been uploaded.
                 * Used when sync = false
                 */
                tempFiles.shift();
                
                /**
                 * Upload the next available
                 * file on queue.
                 * 
                 * Added a delay for
                 * some cool effects.
                 */
                setTimeout(function(){
                    $().upload(tempFiles);
                }, 500);
                
                /**
                 * Show to the user the updated file counter.
                 */
                $().updateCounter();
            });
        },
        
        /**
         * Function that will show server response
         * and will create a 'delete' button.
         * @param div
         * div='The div where the button will be added'.
         * @param response
         * response='A JSON form response message
         * from the server.'
         */
        showResponse: function(div, response){
            var deleteButton = $().createInput({type:'button', name:'button', cssClass:'btn btn-xs btn-danger ' + __options.setAnimSpeed[__options.animSpeed] + ' rotateHalf_setUp', text:'Delete'})
                                .bind('click', function(){
                                        $(this).parent().parent().fadeOut(200, function(){
                                            $(this).remove(); __counter--; $().updateCounter();
                                        });
                                     }).hide();
            
            div.children().last().find('.progress-bar').css({'width':'100%'});
            div.children().last().animate({"opacity":"toggle"}, 1000, function(){
                $("#" + __options.response).prepend($('<p/>').append(response));
                div.append($('<p/>').append(deleteButton)).append($('<p/>').append('&nbsp;'));
                $().addEffect(deleteButton.fadeIn());
            });
        },
        
        /**
         * Function that will perform the
         * added animation.
         */
        addEffect: function(elem){
            elem.offset();
            return elem.addClass('rotateHalf');
        }
    });
}(jQuery));