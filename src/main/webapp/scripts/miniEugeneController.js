/* Gets a list of images from the server and adds them to the imageList
 * Also adds a unique id to each part, viewable when clicked
 */


$(document).ready(function() {

    //$('#runButton') jquery syntax
    //assigns various functions to the run button when clicked
    $('#runButton').click(function() {
        
    	$('#outputArea').collapse('show');
        
        var text = false; //is the text editor active?

        if ($('div#textEditorTab').hasClass("active")) {
            text = true;
        }
        
        if (text) {
            //Clicking run button sends current text to server
            //May want to modify to send file or collection of files to server(if Eugene program spans multiple files)

            $('#runButton').attr("disabled", "disabled");
            
            var command = {"input": editor.getValue(), 
                           "command":"solve", 
                           "N":$('#sizeOfDesign').val()};

            $.post("MiniEugeneServlet", command, function(response) {
                $('#runButton').removeAttr("disabled");
                //alert(response["status"]);
                if ("good" === response["status"]) {

                    	$('#outputExceptionArea').html('');
                    	$('#exception').removeClass('active');
                        $('#outputExceptionTab').removeClass('active');

                    	// STATISTICS
                    	$('#outputStatsArea').html(response["statistics"]);
                    	$('#stats').removeClass('active');
                        $('#outputStatsTab').removeClass('active');

                    	// SOLUTIONS TAB
                    	$('#outputEugeneArea').html('<div>Download the Eugene header file <a target="_blank" href=' + response["eugene"] + '>here</a></div>');
                    	$('#eugene').removeClass('active');
                        $('#outputEugeneTab').removeClass('active');

                    	// SBOL
                    	$('#outputSBOLArea').html('<div>Download the SBOL file <a target="_blank" href=' + response["sbol"] + '>here</a></div>');
                    	$('#sbol').removeClass('active');
                        $('#outputSBOLTab').removeClass('active');

                    	//$('#outputSolutionArea').html('<table class="table table-bordered table-hover" id="solutionList"><tr><td>p, c, r, t</td></tr><tr><td>p, c, t, r</td></tr><tr><td>p, r, c, t</td></tr><tr><td>p, r, t, c</td></tr><tr><td>p, t, c, r</td></tr><tr><td>p, t, r, c</td></tr><tr><td>r, c, p, t</td></tr><tr><td>r, c, t, p</td></tr><tr><td>r, p, t, c</td></tr><tr><td>r, p, c, t</td></tr><tr><td>r, t, c, p</td></tr><tr><td>r, t, p, c</td></tr><tr><td>c, p, r, t</td></tr><tr><td>c, p, t, r</td></tr><tr><td>c, r, p, t</td></tr><tr><td>c, r, t, p</td></tr><tr><td>c, t, r, p</td></tr><tr><td>c, t, p, r</td></tr><tr><td>t, c, p, r</td></tr><tr><td>t, c, r, p</td></tr><tr><td>t, r, p, c</td></tr><tr><td>t, r, c, p</td></tr><tr><td>t, p, r, c</td></tr><tr><td>t, p, c, r</td></tr></table>');
                    	// visualize the designs using pigeon

                        $('#outputImageArea').html('<div class="item active"><img src="' + response["pigeon"] + '"/></div>');
                        $('#visual').addClass('active');                    
                        $('#outputImageTab').addClass('active');

                }
                else if ("exception" === response["status"]) {
                	// clean and deactivate the other tabs
                	$('#visual').removeClass('active');
                    $("#outputImageArea").html('');
                	$('#stats').removeClass('active');
                    $("#outputStatsArea").html('');
                	$('#sbol').removeClass('active');
                    $("#outputSBOLArea").html('');
                	$('#eugene').removeClass('active');
                    $("#outputEugeneArea").html('');

                    // print the exception
                	$('#outputExceptionArea').html(response["results"]);
                	$('#exception').addClass('active');
                    $('#outputExceptionTab').addClass('active');
                }
                
                // regardless what happened, we visualize the ACT
            	$('#outputACTArea').html('<div class="item active"><img src="' + response["act-uri"] + '"/></div>');

            });
        }
    });


    //functions to run on page load
    var editor = CodeMirror.fromTextArea(document.getElementById("textEditor"), {
        styleActiveLine: true,
        lineNumbers: true,
        lineWrapping: true,
        theme: "neat",
        mode: "eugene"
    });

});

