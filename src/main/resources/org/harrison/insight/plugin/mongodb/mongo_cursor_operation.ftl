<#ftl strip_whitespace=true>
<#import "/insight-1.0.ftl" as insight />

<@insight.group label="DBCursor">
    <@insight.entry name="Collection">
    	${operation.collection?html}
    </@insight.entry>
    <@insight.entry name="Query">
    	${operation.query?html}
    </@insight.entry>
    <@insight.entry name="Keys Wanted">
    	${operation.keysWanted?html}
    </@insight.entry>
    <@insight.entry name="Return">
    	${operation.returnValue?html}
    </@insight.entry>
</@insight.group>

<@insight.sourceCodeLocation location=operation.sourceCodeLocation />
