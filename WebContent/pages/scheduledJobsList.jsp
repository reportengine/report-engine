

<%
String buttonName 	= (String)request.getParameter("SUBMIT");

if(buttonName != null){
%>
<%@ include file="include/re_jsp.jsp"%>
<%
	if(buttonName.equalsIgnoreCase("Delete")){
		new ManageJobSchedulerGui().deleteScheduledJob(Integer.valueOf(request.getParameter(Keys.JOB_ID)));
		//new PurgeLogs().deleteSuiteLogs(Integer.valueOf(request.getParameter("radio")));
		response.sendRedirect("scheduledJobsList.jsp");
	}else if(buttonName.equalsIgnoreCase("Enable")){
		new ManageJobSchedulerGui().enableScheduledJob(Integer.valueOf(request.getParameter(Keys.JOB_ID)));
		response.sendRedirect("scheduledJobsList.jsp");
	}else if(buttonName.equalsIgnoreCase("Disable")){
		new ManageJobSchedulerGui().disableScheduledJob(Integer.valueOf(request.getParameter(Keys.JOB_ID)));
		response.sendRedirect("scheduledJobsList.jsp");
	}else if(buttonName.equalsIgnoreCase("Add")){
%>		
		<%@ include file="index-part1.jsp"%>

		<%@ include file="index-part2.jsp"%>
		
		<script type="text/javascript">
		function simpleJobChange(){	
			if($('#<%=Keys.JOB_SIMPLE_JOB_ENABLE%>').is(':checked')){
				$('#<%=Keys.JOB_REPEAT_COUNT_TR%>').show();
				$('#<%=Keys.JOB_REPEAT_INTERVAL_TR%>').show();
				$('#<%=Keys.JOB_FREQUENCY_TR%>').hide();
				$('#<%=Keys.JOB_WEEKDAYS_TR%>').hide();
				$('#<%=Keys.JOB_WEEKDAY_TR%>').hide();
				$('#<%=Keys.JOB_DAY_OF_MONTH_TR%>').hide();
				$('#<%=Keys.JOB_ONE_TIME_DATE_TR%>').hide();
				$('#<%=Keys.JOB_CRON_EXPRESSION_ENABLED_TR%>').hide();
				$('#<%=Keys.JOB_CRON_EXPRESSION_TR%>').hide();
				$('#<%=Keys.JOB_TRIGGER_TIME_TR%>').hide();
			}else{
				$('#<%=Keys.JOB_REPEAT_COUNT_TR%>').hide();
				$('#<%=Keys.JOB_REPEAT_INTERVAL_TR%>').hide();
				$('#<%=Keys.JOB_FREQUENCY_TR%>').show();
				$('#<%=Keys.JOB_WEEKDAYS_TR%>').show();
				$('#<%=Keys.JOB_WEEKDAY_TR%>').show();
				$('#<%=Keys.JOB_DAY_OF_MONTH_TR%>').show();
				$('#<%=Keys.JOB_ONE_TIME_DATE_TR%>').show();
				$('#<%=Keys.JOB_CRON_EXPRESSION_ENABLED_TR%>').show();
				$('#<%=Keys.JOB_CRON_EXPRESSION_TR%>').show();
				$('#<%=Keys.JOB_TRIGGER_TIME_TR%>').show();
				cronChange();
			}
		}
		function cronChange(){	
			if($('#<%=Keys.JOB_CRON_EXPRESSION_ENABLED%>').is(':checked')){
				$('#<%=Keys.JOB_CRON_EXPRESSION_TR%>').show();
				$('#<%=Keys.JOB_FREQUENCY_TR%>').hide();
				$('#<%=Keys.JOB_WEEKDAYS_TR%>').hide();
				$('#<%=Keys.JOB_WEEKDAY_TR%>').hide();
				$('#<%=Keys.JOB_DAY_OF_MONTH_TR%>').hide();
				$('#<%=Keys.JOB_ONE_TIME_DATE_TR%>').hide();
				$('#<%=Keys.JOB_TRIGGER_TIME_TR%>').hide();
			}else{
				$('#<%=Keys.JOB_CRON_EXPRESSION_TR%>').hide();
				$('#<%=Keys.JOB_FREQUENCY_TR%>').show();
				$('#<%=Keys.JOB_WEEKDAYS_TR%>').show();
				$('#<%=Keys.JOB_WEEKDAY_TR%>').show();
				$('#<%=Keys.JOB_DAY_OF_MONTH_TR%>').show();
				$('#<%=Keys.JOB_ONE_TIME_DATE_TR%>').show();
				$('#<%=Keys.JOB_TRIGGER_TIME_TR%>').show();
				frequencyChange();
			}
		}
		function frequencyChange(){	
			$('#<%=Keys.JOB_END_LESS_TR%>').show();
			endLessJobChange();
			if($('#<%=Keys.JOB_FREQUENCY%>').val() == '<%=Keys.JOB_FREQUENCY_DAILY%>'){
				$('#<%=Keys.JOB_WEEKDAYS_TR%>').show();
				$('#<%=Keys.JOB_WEEKDAY_TR%>').hide();
				$('#<%=Keys.JOB_DAY_OF_MONTH_TR%>').hide();
				$('#<%=Keys.JOB_ONE_TIME_DATE_TR%>').hide();
			}else if($('#<%=Keys.JOB_FREQUENCY%>').val() == '<%=Keys.JOB_FREQUENCY_WEEKLY%>'){
				$('#<%=Keys.JOB_WEEKDAYS_TR%>').hide();
				$('#<%=Keys.JOB_WEEKDAY_TR%>').show();
				$('#<%=Keys.JOB_DAY_OF_MONTH_TR%>').hide();
				$('#<%=Keys.JOB_ONE_TIME_DATE_TR%>').hide();
			}else if($('#<%=Keys.JOB_FREQUENCY%>').val() == '<%=Keys.JOB_FREQUENCY_MONTHLY%>'){
				$('#<%=Keys.JOB_WEEKDAYS_TR%>').hide();
				$('#<%=Keys.JOB_WEEKDAY_TR%>').hide();
				$('#<%=Keys.JOB_DAY_OF_MONTH_TR%>').show();
				$('#<%=Keys.JOB_ONE_TIME_DATE_TR%>').hide();
			}else{
				$('#<%=Keys.JOB_WEEKDAYS_TR%>').hide();
				$('#<%=Keys.JOB_WEEKDAY_TR%>').hide();
				$('#<%=Keys.JOB_DAY_OF_MONTH_TR%>').hide();
				$('#<%=Keys.JOB_ONE_TIME_DATE_TR%>').show();
				$('#<%=Keys.JOB_END_LESS_TR%>').hide();
				$('#<%=Keys.JOB_DATE_FROM_TR%>').hide();
				$('#<%=Keys.JOB_DATE_TO_TR%>').hide();
			}
		}
		function endLessJobChange(){
			if($('#<%=Keys.JOB_END_LESS_ENABLED%>').is(':checked')){
				$('#<%=Keys.JOB_DATE_FROM_TR%>').hide();
				$('#<%=Keys.JOB_DATE_TO_TR%>').hide();
			}else{
				$('#<%=Keys.JOB_DATE_FROM_TR%>').show();
				$('#<%=Keys.JOB_DATE_TO_TR%>').show();
			}
		}
</script>
<script type="text/javascript">
	$(document).ready(function(){
		simpleJobChange();
		endLessJobChange();
	});
</script>

		<div id="dt_page">
		<div id="container">
		<h1>Add New Schedule:</h1>

		<form method="post" name="jobDetails" action="scheduledJobsList.jsp"> 
		<table border="0" cellpadding="3" align="left" id="dt_table">
		
		<tr>
			<td align="left">Job Name</td>
			<td>:</td>
			<td colspan="2"><input type="text" name="<%=Keys.JOB_NAME%>" size="8" value=""  style="width:320px;"></td>	
		</tr>
		
	
		<tr>
			<td align="left">Job Type</td>
			<td>:</td>
			<td colspan="2"><select name="<%=Keys.JOB_TYPE%>"><option value="3">Email Test Reports</option></select></td>	
		</tr>
		
		<tr>
			<td align="left">Reference</td>
			<td>:</td>
			<td colspan="2"><select name="<%=Keys.JOB_REFERENCE%>"> 
			<%
 				ArrayList<ReportGroup> reportGroups = new ManageReportGroup().getAllReportGroup();
 			 				for(ReportGroup reportGroup: reportGroups){
 			 					out.println("<option value=\""+reportGroup.getId()+"\">"+reportGroup.getGroupName()+"</option>");
 			 				}
 			%>			
			</select></td>	
		</tr>
		
		<tr>
			<td align="left">Enabled</td>
			<td>:</td>
			<td colspan="2"><input type="checkbox" name="<%=Keys.JOB_ENABLED%>" id="<%=Keys.JOB_ENABLED%>" value="Enabled" checked></td>	
		</tr>
		
		<tr>
			<td align="left">Simple Job</td>
			<td>:</td>
			<td colspan="2"><input type="checkbox" name="<%=Keys.JOB_SIMPLE_JOB_ENABLE%>" id="<%=Keys.JOB_SIMPLE_JOB_ENABLE%>" value="simpleJobEnabled" onchange="simpleJobChange(this)" checked></td>
		</tr>
		
		<tr id="<%=Keys.JOB_REPEAT_COUNT_TR%>">
			<td align="left">Repeat Count</td>
			<td>:</td>
			<td colspan="2"><input type="text" id="<%=Keys.JOB_REPEAT_COUNT%>" name="<%=Keys.JOB_REPEAT_COUNT%>" size="8" value=""  style="width:100px;"></td>
		</tr>		
		
		<tr id="<%=Keys.JOB_REPEAT_INTERVAL_TR%>">
			<td align="left">Repeat Interval (Sec)</td>
			<td>:</td>
			<td colspan="2"><input type="text" id="<%=Keys.JOB_REPEAT_INTERVAL%>" name="<%=Keys.JOB_REPEAT_INTERVAL%>" size="8" value=""  style="width:100px;"></td>
		</tr>
		
		<tr id="<%=Keys.JOB_CRON_EXPRESSION_ENABLED_TR%>">
			<td align="left">Enable Cron</td>
			<td>:</td>
			<td colspan="2"><input type="checkbox" name="<%=Keys.JOB_CRON_EXPRESSION_ENABLED%>" id="<%=Keys.JOB_CRON_EXPRESSION_ENABLED%>" value="cronEnabled" checked onchange="cronChange(this)"></td>	
		</tr>
		
		<tr id="<%=Keys.JOB_CRON_EXPRESSION_TR%>">
			<td align="left">Cron Expression</td>
			<td>:</td>
			<td colspan="2"><input type="text" name="<%=Keys.JOB_CRON_EXPRESSION%>" size="8" value=""  style="width:270px;"></td>	
		</tr>
		
		
		<tr id="<%=Keys.JOB_FREQUENCY_TR%>">
			<td align="left">Frequency</td>
			<td>:</td>
			<td colspan="2"><select name="<%=Keys.JOB_FREQUENCY%>" id="<%=Keys.JOB_FREQUENCY%>" style="width:120px;" onchange="frequencyChange(this)"><option value="<%=Keys.JOB_FREQUENCY_DAILY%>"><%=Keys.JOB_FREQUENCY_DAILY%></option><option value="<%=Keys.JOB_FREQUENCY_WEEKLY%>"><%=Keys.JOB_FREQUENCY_WEEKLY%></option><option value="<%=Keys.JOB_FREQUENCY_MONTHLY%>"><%=Keys.JOB_FREQUENCY_MONTHLY%></option><option value="<%=Keys.JOB_FREQUENCY_ONE_TIME%>"><%=Keys.JOB_FREQUENCY_ONE_TIME%></option></select></td>
		</tr>
		
		<tr id="<%=Keys.JOB_TRIGGER_TIME_TR%>">
			<td align="left">Trigger Time</td>
			<td>:</td>
			<td colspan="2" align="left"> <select name="<%=Keys.JOB_TRIGGER_HOUR%>" style="width:50px;"><option selected>00</option><option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option><option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option>23</option></select>:<select name="<%=Keys.JOB_TRIGGER_MINUTE%>" style="width:50px;" length="10"><option selected>00</option><option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option><option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option>23</option><option>24</option><option>25</option><option>26</option><option>27</option><option>28</option><option>29</option><option>30</option><option>31</option><option>32</option><option>33</option><option>34</option><option>35</option><option>36</option><option>37</option><option>38</option><option>39</option><option>40</option><option>41</option><option>42</option><option>43</option><option>44</option><option>45</option><option>46</option><option>47</option><option>48</option><option>49</option><option>50</option><option>51</option><option>52</option><option>53</option><option>54</option><option>55</option><option>56</option><option>57</option><option>58</option><option>59</option> </select>:<select name="<%=Keys.JOB_TRIGGER_SECOND%>" style="width:50px;"><option selected>00</option><option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option><option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option>23</option><option>24</option><option>25</option><option>26</option><option>27</option><option>28</option><option>29</option><option>30</option><option>31</option><option>32</option><option>33</option><option>34</option><option>35</option><option>36</option><option>37</option><option>38</option><option>39</option><option>40</option><option>41</option><option>42</option><option>43</option><option>44</option><option>45</option><option>46</option><option>47</option><option>48</option><option>49</option><option>50</option><option>51</option><option>52</option><option>53</option><option>54</option><option>55</option><option>56</option><option>57</option><option>58</option><option>59</option> </select></td>
		</tr>
		
		
		<tr id="<%=Keys.JOB_WEEKDAYS_TR%>">
			<td align="left">Day(s)</td>
			<td>:</td>
			<td colspan="2">
				<table align="left" id="box-table" summary="Week day">
					<thead>
						<tr> 
							<th scope="col" align="center" class="WDT1" nowrap="nowrap"><b>Sun</b></th>
							<th scope="col" align="center" class="WDT2" nowrap="nowrap"><b>Mon</b></th>
							<th scope="col" align="center" class="WDT3" nowrap="nowrap"><b>Tue</b></th>
							<th scope="col" align="center" class="WDT4" nowrap="nowrap"><b>Wed</b></th>
							<th scope="col" align="center" class="WDT5" nowrap="nowrap"><b>Thu</b></th>		
							<th scope="col" align="center" class="WDT6" nowrap="nowrap"><b>Fri</b></th>
							<th scope="col" align="center" class="WDT7" nowrap="nowrap"><b>Sat</b></th>	
						</tr>
					</thead>
					<tbody>
					<tr>
						<td align="center"><input type="checkbox" name="<%=Keys.JOB_WEEKDAYS%>" value="SUN" checked></td>
						<td align="center"><input type="checkbox" name="<%=Keys.JOB_WEEKDAYS%>" value="MON" checked></td>
						<td align="center"><input type="checkbox" name="<%=Keys.JOB_WEEKDAYS%>" value="TUE" checked></td>
						<td align="center"><input type="checkbox" name="<%=Keys.JOB_WEEKDAYS%>" value="WED" checked></td>
						<td align="center"><input type="checkbox" name="<%=Keys.JOB_WEEKDAYS%>" value="THU" checked></td>
						<td align="center"><input type="checkbox" name="<%=Keys.JOB_WEEKDAYS%>" value="FRI" checked></td>
						<td align="center"><input type="checkbox" name="<%=Keys.JOB_WEEKDAYS%>" value="SAT" checked></td>
					</tr>
					</tbody>
				</table>
			</td>
		</tr>
		
		<tr id="<%=Keys.JOB_WEEKDAY_TR%>"> 
			<td align="left">Day</td>
			<td>:</td>
			<td colspan="2">
				<table align="left" id="box-table" summary="Week day Week">
				<thead>
				<tr>
					<th scope="col" align="center" class="WDT1" nowrap="nowrap"><b>Sun</b></th>
					<th scope="col" align="center" class="WDT2" nowrap="nowrap"><b>Mon</b></th>
					<th scope="col" align="center" class="WDT3" nowrap="nowrap"><b>Tue</b></th>
					<th scope="col" align="center" class="WDT4" nowrap="nowrap"><b>Wed</b></th>
					<th scope="col" align="center" class="WDT5" nowrap="nowrap"><b>Thu</b></th>	
					<th scope="col" align="center" class="WDT6" nowrap="nowrap"><b>Fri</b></th>
					<th scope="col" align="center" class="WDT7" nowrap="nowrap"><b>Sat</b></th>
				</tr>
			</thead>
			<tbody>
			<tr>
				<td align="center"><input type="radio" name="<%=Keys.JOB_WEEKDAY%>" value="SUN"></td>
				<td align="center"><input type="radio" name="<%=Keys.JOB_WEEKDAY%>" value="MON" checked></td>
				<td align="center"><input type="radio" name="<%=Keys.JOB_WEEKDAY%>" value="TUE"></td>
				<td align="center"><input type="radio" name="<%=Keys.JOB_WEEKDAY%>" value="WED"></td>
				<td align="center"><input type="radio" name="<%=Keys.JOB_WEEKDAY%>" value="THU"></td>
				<td align="center"><input type="radio" name="<%=Keys.JOB_WEEKDAY%>" value="FRI"></td>
				<td align="center"><input type="radio" name="<%=Keys.JOB_WEEKDAY%>" value="SAT"></td>
			</tr>
			</tbody>
			</table>
		</td>
	</tr>
	
		
	<tr id="<%=Keys.JOB_DAY_OF_MONTH_TR%>">
		<td align="left">Day of Month</td>
		<td>:</td>
		<td colspan="2">
			<select name="<%=Keys.JOB_DAY_OF_MONTH%>" value=""  style="width:50px;"> <option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option><option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option>23</option><option>24</option><option>25</option><option>26</option><option>27</option><option>28</option><option>29</option><option>30</option><option>31</option></select>
		</td>
	</tr>	
		
	<tr id=<%=Keys.JOB_ONE_TIME_DATE_TR%>>
		<td align="left">Date</td>
		<td>:</td>
		<td colspan="2">
			<table >
				<tr><td><input type="text" id="<%=Keys.JOB_ONE_TIME_DATE%>" name="<%=Keys.JOB_ONE_TIME_DATE%>" size="8" readonly value=""  style="width:100px;"></td></tr>			
			</table>
		</td>
	</tr>
		
	<tr id="<%=Keys.JOB_END_LESS_TR%>">
		<td align="left">Endless Job</td>
		<td>:</td>
		<td colspan="2"><input type="checkbox" name="<%=Keys.JOB_END_LESS_ENABLED%>" id="<%=Keys.JOB_END_LESS_ENABLED%>" value="Enabled" onchange="endLessJobChange(this)" checked></td>
	</tr>	
			
	<tr id=<%=Keys.JOB_DATE_FROM_TR%>>
		<td align="left">Valid From</td>
		<td>:</td>
		<td colspan="2">
			<table >
				<tr><td><input type="text" id="<%=Keys.JOB_DATE_FROM%>" name="<%=Keys.JOB_DATE_FROM%>" size="8" readonly value=""  style="width:100px;"></td><td> <select name="<%=Keys.JOB_DATE_FROM_HOUR%>" style="width:50px;"><option selected>00</option><option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option><option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option>23</option></select></td><td align="left"><select name="<%=Keys.JOB_DATE_FROM_MINUTE%>" style="width:50px;" length="10"><option selected>00</option><option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option><option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option>23</option><option>24</option><option>25</option><option>26</option><option>27</option><option>28</option><option>29</option><option>30</option><option>31</option><option>32</option><option>33</option><option>34</option><option>35</option><option>36</option><option>37</option><option>38</option><option>39</option><option>40</option><option>41</option><option>42</option><option>43</option><option>44</option><option>45</option><option>46</option><option>47</option><option>48</option><option>49</option><option>50</option><option>51</option><option>52</option><option>53</option><option>54</option><option>55</option><option>56</option><option>57</option><option>58</option><option>59</option> </select></td></tr>			
			</table>
		</td>
	</tr>
				
	<tr id=<%=Keys.JOB_DATE_TO_TR%>>
		<td align="left">Valid To</td>
		<td>:</td>
		<td colspan="2">
			<table >
				<tr><td><input type="text" id="<%=Keys.JOB_DATE_TO%>" name="<%=Keys.JOB_DATE_TO%>" size="8" readonly value=""  style="width:100px;"></td><td> <select name="<%=Keys.JOB_DATE_TO_HOUR%>" style="width:50px;"><option selected>00</option><option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option><option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option>23</option></select></td><td align="left"><select name="<%=Keys.JOB_DATE_TO_MINUTE%>" style="width:50px;" length="10"><option selected>00</option><option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option><option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option>23</option><option>24</option><option>25</option><option>26</option><option>27</option><option>28</option><option>29</option><option>30</option><option>31</option><option>32</option><option>33</option><option>34</option><option>35</option><option>36</option><option>37</option><option>38</option><option>39</option><option>40</option><option>41</option><option>42</option><option>43</option><option>44</option><option>45</option><option>46</option><option>47</option><option>48</option><option>49</option><option>50</option><option>51</option><option>52</option><option>53</option><option>54</option><option>55</option><option>56</option><option>57</option><option>58</option><option>59</option> </select></td></tr>			
			</table>
		</td>
	</tr>
				
		<tr> <td colspan="4" align="right"> <div class="jbutton"><input type="submit" name="SUBMIT" value="Submit" style="width:80px;"></div></td></tr>
		</table>
		</form>


		</div>
		</div>
		<%@ include file="index-part3.jsp"%>
<%
	}else if(buttonName.equalsIgnoreCase("Submit")){
		new ManageJobSchedulerGui().addNewUserJob(request, session);
		response.sendRedirect("scheduledJobsList.jsp");
	}
}else{
%>
<%@ include file="index-part1.jsp"%>

<script type="text/javascript">
	function callMeOnTableChange(){
		
		$(".ajax").colorbox({rel:'ajax', scrolling:"true",width:"40%", height:"70%"});

		//Delete Confirmation,
		$(function() {
			$( "#deleteDialog" ).dialog({
				autoOpen: false,
				resizable: false,
				height:140,
				modal: true,
				hide: "explode",
				buttons: {
					"Yes": function() {
						$(this).dialog('close'); 						
						 $('<input />').attr('type', 'hidden')
            					.attr('name', 'SUBMIT')
            					.attr('value', 'Delete')
            					.appendTo('#manage-reports');
						$('#manage-reports').submit();
						$( "input[Value=Delete]" ).button( "option", "disabled", true );
					},
					"No": function() {
						$( this ).dialog( "close" );
						return false;
					}
				}
			});

		});

		//Enable Delete Button if we click radio
		$('input[type=radio]').click(function() { 
			//$("input[Value=Delete]").removeAttr("disabled");
			$( "input[Value=Delete]" ).button( "option", "disabled", false );
			$( "input[Value=Enable]" ).button( "option", "disabled", false );
			$( "input[Value=Disable]" ).button( "option", "disabled", false );
		});

		//Unselect All radio buttons
		$('input[type=radio]').prop('checked', false);	


		//Call Confirmation once clicked Delete Button
		$("input[Value=Delete]").click(function() { 
			$("#deleteDialog").dialog('open');	 
			return false;
		});

		//Disable Delete Button on load
		$( "input[Value=Edit]" ).button( "option", "disabled", true );
		$( "input[Value=Delete]" ).button( "option", "disabled", true );
		$( "input[Value=Enable]" ).button( "option", "disabled", true );
		$( "input[Value=Disable]" ).button( "option", "disabled", true );
	};
</script>


<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		oTable = $('#dt_table').dataTable({
			"fnDrawCallback": function() {      
				callMeOnTableChange();
    			},
			"bJQueryUI": true,
			"bPaginate": true,
			"sPaginationType": "full_numbers",
			"aaSorting": [[1,'asc']],
			"aoColumns": [
			{ "bSortable": false },
			null,
			null,
			{ "bSortable": false },
			{ "sType": "test-total" },
			{ "sType": "test-total" },
			{ "sType": "test-total" },
			{ "sType": "test-total" },
			{ "sType": "test-total" },
			null,
			{ "sType": "date-gui" },
			{ "sType": "date-gui" },
			{ "sType": "duration-gui" }
			],
			"iDisplayLength":15
			});
} );			
</script>
<script type="text/javascript">
	$(document).ready(function(){
		callMeOnTableChange();
	});
</script>

<%@ include file="index-part2.jsp"%>


 
<div id="dt_page">
<div id="container">

<h1>Available Schedules:</h1>
<form method="post" id="manage-reports" name="manageReports" action="scheduledJobsList.jsp" >
<table cellpadding="0" cellspacing="0" border="0" class="display" id="dt_table">
	<thead>
		<tr>
			<th></th>	
			<th>S.No</th>
			<th>Enabled</th>
			<th>Name</th>	
			<th>Type</th>
			<th>Cron</th>
			<th>Frequency</th>
			<th>Time</th>
			<th>Repeat Count</th>
			<th>Repeat Interval</th>
            <th>Valid From</th>
            <th>Valid To</th>
            <th>Creation Time</th>
		</tr>
	</thead>
	<tbody>

	<%
		ArrayList<JobScheduler> userJobs= null;
			userJobs = new ManageJobSchedulerGui().getAllUserJobs();		
			
			for(int i = 0; i<userJobs.size(); i++){				
				out.println("<tr><td align=\"center\"><input type=\"radio\" name=\""+Keys.JOB_ID+"\" value=\""+userJobs.get(i).getId()+"\"><td>"+(i+1)+"</td><td align=\"center\"><img width=\"16\" height=\"16\"  src='../images/icons/"+userJobs.get(i).isJobEnabled()+".png' alt='"+userJobs.get(i).isJobEnabled()+"'></td><td>"+userJobs.get(i).getJobName()+"</td><td>"+userJobs.get(i).getTargetClassDescription()+"</td><td nowrap>"+General.getNotNullString(userJobs.get(i).getCronExpression())+"</td><td>"+General.getJobFrequency(userJobs.get(i).getJobFrequency(), userJobs.get(i).getJobWeekday())+"</td><td>"+General.getHourMinute(userJobs.get(i).getJobExecutionTime())+"</td><td>"+General.getNotNullString(userJobs.get(i).getRepeatCount())+"</td><td>"+General.getNotNullString(userJobs.get(i).getRepeatInterval())+"</td><td>"+General.getGuiDateTime(userJobs.get(i).getValidFromTime())+"</td><td>"+General.getGuiDateTime(userJobs.get(i).getValidToTime())+"</td><td>"+General.getGuiDateTime(userJobs.get(i).getCreationTime())+"</td></tr>");	
			}
	%>
    </tbody>
</table>

<BR>
<div class="jbutton">
	<input type="submit" name="SUBMIT" value="Add" style="width:80px;"> <input type="submit" name="SUBMIT" value="Edit" style="width:80px;"> <input type="submit" name="SUBMIT" value="Delete" style="width:80px;"> <input type="submit" name="SUBMIT" value="Enable" style="width:80px;"> <input type="submit" name="SUBMIT" value="Disable" style="width:80px;">
</div>
</form>
<BR>
<BR>
<table cellpadding="0" cellspacing="0" border="0" id="dt_table">
<tr>
<td><img width="16" height="16"  src='../images/icons/true.png'  alt='Enabled'></td><td valign="top">- Enabled&nbsp;</td>
<td><img width="16" height="16"  src='../images/icons/false.png' alt='Disabled'></td><td valign="top">- Disabled&nbsp;</td> 
<tr>
</table>


<div id="deleteDialog" title="Delete Confirmation!">
	<p>&nbsp;You are about to delete this item.<br> 
	&nbsp;It cannot be restored at a later time! Continue?</p>
</div>

</div>
</div>
<%@ include file="index-part3.jsp"%>
<%}%>

