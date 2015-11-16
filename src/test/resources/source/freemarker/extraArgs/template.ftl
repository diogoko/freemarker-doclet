<ul>
<#list options() as opt><#if opt[0] == "-extra"><li>${opt[1]} -- ${opt[2]}</li></#if></#list>
</ul>
