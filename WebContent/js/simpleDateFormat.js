/**
 * SimpledateFormat javascript class
 *
 * var reverseDateFormat = new SimpleDateFormat("YYYYMMdd");
 * var reverseDate = reverseDateFormat.format(new Date());
 * http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat.html
 *
 * **/
function SimpleDateFormat(formatString){
 this.formatString = formatString;
 this.monthNames = ["January","February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
 this.dayNames =   ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];

 this.format = function(aDate){
 var localFormatString = formatString;
 // The order is significant
 /* YYYY */  localFormatString = localFormatString.replace(/Y{3,}/g,     "\" + (aDate.getFullYear()) + \"");
 /* YY   */  localFormatString = localFormatString.replace(/Y{2}/g,      "\" + ((aDate.getFullYear()).toString().substring(2)) + \"");
 /* MMMM */  localFormatString = localFormatString.replace(/M{4,}/g,     "\" + (this.monthNames[aDate.getMonth()]) + \"");
 /* MMM  */  localFormatString = localFormatString.replace(/M{3}/g,      "\" + ((this.monthNames[aDate.getMonth()]).substring(0,3)) + \"");
 /* MM   */  localFormatString = localFormatString.replace(/M{2}/g,      "\" + (aDate.getMonth()+101).toString().substring(1) + \"");
 /* ww   */  /* don't have time to implement this today */
 /* WW   */  /* don't have time to implement this today */
 /* DD   */  /* don't have time to implement this today */
 /* dd   */  localFormatString = localFormatString.replace(/d{2}/g,      "\" + (aDate.getDate()+100).toString().substring(1) + \"");
 /* FF   */  localFormatString = localFormatString.replace(/F{2}/g,      "\" + (aDate.getDay()+100).toString().substring(1) + \"");
 /* EEEE */  localFormatString = localFormatString.replace(/E{4,}/g,     "\" + (this.dayNames[aDate.getDay()]) + \"");
 /* EEE  */  localFormatString = localFormatString.replace(/E{3}/g,      "\" + ((this.dayNames[aDate.getDay()]).substring(0,3)) + \"");
 /* EE   */  localFormatString = localFormatString.replace(/E{2}/g,      "\" + (aDate.getDay()+100).toString().substring(1) + \"");
 /* a    */  /* don't have time to implement this today */
 /* HH   */  localFormatString = localFormatString.replace(/H{2}/g,      "\" + (aDate.getHours()+100).toString().substring(1) + \"");
 /* kk   */  localFormatString = localFormatString.replace(/k{2}/g,      "\" + (aDate.getHours()+101).toString().substring(1) + \"");
 /* KK   */  /* don't have time to implement this today */
 /* hh   */  /* don't have time to implement this today */
 /* mm   */  localFormatString = localFormatString.replace(/m{2}/g,      "\" + (aDate.getMinutes()+100).toString().substring(1) + \"");
 /* ss   */  localFormatString = localFormatString.replace(/s{2}/g,      "\" + (aDate.getSeconds()+100).toString().substring(1) + \"");
 /* SS   */  localFormatString = localFormatString.replace(/S{2}/g,      "\" + (aDate.getMilliSeconds()+1000).toString().substring(1) + \"");
 /* z   */   /* don't have time to implement this today */

 localFormatString = "\"" + localFormatString + "\"";
 //prompt("localFormatString", localFormatString);
 var formatedDate = eval(localFormatString);
 return(formatedDate);
 }
}
