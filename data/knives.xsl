<?xml version="1.0" encoding="UTF-8"?>
<!--
  Splits the flat list of knives into sub-lists grouped by <Type>
  (Muenchian grouping, XSLT 1.0). The Type element is moved to the group attribute.
-->
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://lab2.example.com/knives"
                xmlns:k="http://lab2.example.com/knives"
                exclude-result-prefixes="k">

    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>
    <xsl:strip-space elements="*"/>

    <xsl:key name="knives-by-type" match="k:Knife" use="k:Type"/>

    <xsl:template match="/k:Knives">
        <KnivesByType>
            <xsl:for-each select="k:Knife[generate-id() = generate-id(key('knives-by-type', k:Type)[1])]">
                <xsl:sort select="k:Type"/>
                <TypeGroup type="{k:Type}" count="{count(key('knives-by-type', k:Type))}">
                    <xsl:for-each select="key('knives-by-type', k:Type)">
                        <xsl:sort select="k:Visual/k:Blade/k:Length" data-type="number"/>
                        <Knife id="{@id}">
                            <xsl:apply-templates select="k:Handy | k:Origin | k:Visual | k:Value" mode="rebuild"/>
                        </Knife>
                    </xsl:for-each>
                </TypeGroup>
            </xsl:for-each>
        </KnivesByType>
    </xsl:template>

    <!-- Rebuilds elements without copying unrelated in-scope namespace declarations (e.g. xsi) -->
    <xsl:template match="*" mode="rebuild">
        <xsl:element name="{local-name()}">
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates mode="rebuild"/>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
