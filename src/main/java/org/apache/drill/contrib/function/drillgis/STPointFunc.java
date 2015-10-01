package org.apache.drill.contrib.function.drillgis;

import javax.inject.Inject;

import org.apache.drill.exec.expr.DrillSimpleFunc;
import org.apache.drill.exec.expr.annotations.FunctionTemplate;
import org.apache.drill.exec.expr.annotations.Output;
import org.apache.drill.exec.expr.annotations.Param;
import org.apache.drill.exec.expr.holders.Float8Holder;
import org.apache.drill.exec.expr.holders.VarBinaryHolder;

import io.netty.buffer.DrillBuf;

@FunctionTemplate(name = "st_point", scope = FunctionTemplate.FunctionScope.SIMPLE, nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
public class STPointFunc implements DrillSimpleFunc {
	@Param
	Float8Holder lonParam;
	
	@Param
	Float8Holder latParam;
	
	
    @Output
    VarBinaryHolder out;

    @Inject
    DrillBuf buffer;
    

	public void setup() {
	}

	public void eval() {

		double lon = lonParam.value;
		double lat = latParam.value;

		com.vividsolutions.jts.geom.Point point = new com.vividsolutions.jts.geom.GeometryFactory()
				.createPoint(new com.vividsolutions.jts.geom.Coordinate(lon, lat));
		byte[] wkbPoint = new com.vividsolutions.jts.io.WKBWriter().write(point);
		//System.out.println(wkbPoint.toString());

		out.buffer = buffer;
	    out.start = 0;
	    out.end = wkbPoint.length;
	    buffer.setBytes(0, wkbPoint);
	}
}
