package com.lying.variousequipment.client.model;

import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;

public class Matrix4d
{
	private NonNullList<Double> values = NonNullList.<Double>withSize(4 * 4, 0D);
	
	public Matrix4d()
	{
		setVal(0, 0, 1D);
		setVal(1, 1, 1D);
		setVal(2, 2, 1D);
		setVal(3, 3, 1D);
	}
	
	public Matrix4d(double a1In, double b1In, double c1In, double a2In, double b2In, double c2In, double a3In, double b3In, double c3In)
	{
		this();
		setVal(0, 0, a1In);
		setVal(0, 1, b1In);
		setVal(0, 2, c1In);
		
		setVal(1, 0, a2In);
		setVal(1, 1, b2In);
		setVal(1, 2, c2In);
		
		setVal(2, 0, a3In);
		setVal(2, 1, b3In);
		setVal(2, 2, c3In);
	}
	
	public Matrix4d(Quaternion quaternionIn)
	{
		this();
		float quatX = quaternionIn.getX();
		float quatY = quaternionIn.getY();
		float quatZ = quaternionIn.getZ();
		float quatW = quaternionIn.getW();
		
		double f4 = 2D * quatX * quatX;
		double f5 = 2D * quatY * quatY;
		double f6 = 2D * quatZ * quatZ;
		double f7 = quatX * quatY;
		double f8 = quatY * quatZ;
		double f9 = quatZ * quatX;
		double f10 = quatX * quatW;
		double f11 = quatY * quatW;
		double f12 = quatZ * quatW;
		
		setVal(0, 0, 1D - f5 - f6);
		setVal(1, 1, 1D - f6 - f4);
		setVal(2, 2, 1D - f4 - f5);
		setVal(3, 3, 1D);
		
		setVal(1, 0, 2D * (f7 + f12));
		setVal(0, 1, 2D * (f7 - f12));
		setVal(2, 0, 2D * (f9 - f11));
		setVal(0, 2, 2D * (f9 + f11));
		setVal(2, 1, 2D * (f8 + f10));
		setVal(1, 2, 2D * (f8 - f10));
	}
	
	public String toString()
	{
		String s = "";
		for(int row=0; row < 4; row++)
		{
			NonNullList<Double> entries = getRow(row);
			s += "[" + entries.get(0) + ", " + entries.get(1) + ", " + entries.get(2) + ", " + entries.get(3) + "]";
			if(row != 3)
				s += ", ";
		}
		return s;
	}
	
	public Matrix4d copy()
	{
		Matrix4d matrix = new Matrix4d();
		matrix.values = this.values;
		return matrix;
	}
	
	public NonNullList<Double> getRow(int index)
	{
		index %= 4;
		NonNullList<Double> row = NonNullList.<Double>withSize(4, 0D);
		if(index <= 3)
			for(int i=0; i<4; i++)
				row.set(i, getVal(i + (index * 4)));
		return row;
	}
	
	public NonNullList<Double> getColumn(int index)
	{
		index %= 4;
		NonNullList<Double> column = NonNullList.<Double>withSize(4, 0D);
		if(index <= 3)
			for(int i=0; i<4; i++)
				column.set(i, this.values.get(index + (i * 4)));
		
		return column;
	}
	
	public double getVal(int index){ return this.values.get(index % this.values.size()); }
	public double getVal(int row, int col){ return getVal(col + (row * 4)); }
	
	public void setVal(int index, double val){ this.values.set(index, val); }
	public void setVal(int row, int col, double val){ setVal(col + (row * 4), val); }
	
	public Matrix4d mul(Matrix4d matrix2){ return mul(this, matrix2); }
	public Matrix4d div(Matrix4d matrix2){ return div(this, matrix2); }
	public Matrix4d add(Matrix4d matrix2){ return add(this, matrix2); }
	
	/** Returns the entire matrix with opposite polarity */
	public Matrix4d negative()
	{
		Matrix4d matrix = new Matrix4d();
		
		for(int row=0; row<4; row++)
			for(int col=0; col<4; col++)
				matrix.setVal(row, col, -getRow(row).get(col));
		
		return matrix;
	}
	
	/** Returns the entire matrix to the -1, ie 1 / matrix */
	public Matrix4d inverse()
	{
		Matrix4d matrix = new Matrix4d();
		
		for(int row=0; row<4; row++)
			for(int col=0; col<4; col++)
				matrix.setVal(row, col, 1D / getRow(row).get(col));
		
		return matrix;
	}
	
	/** Converts a 3D vector into a translation matrix */
	public static Matrix4d translation(Vector3d vec)
	{
		Matrix4d matrix = new Matrix4d();
		matrix.setVal(0, 3, vec.x);
		matrix.setVal(1, 3, vec.y);
		matrix.setVal(2, 3, vec.z);
		return matrix;
	}
	
	public static Matrix4d mul(Matrix4d matrix1, Matrix4d matrix2)
	{
		Matrix4d matrix3 = new Matrix4d();
		for(int row=0; row<4; row++)
		{
			NonNullList<Double> rowVals = matrix1.getRow(row);
			for(int col=0; col<4; col++)
			{
				NonNullList<Double> colVals = matrix2.getColumn(col);
				
				double tally = 0;
				for(int j = 0; j < 4; j++)
					tally += rowVals.get(j) * colVals.get(j);
				matrix3.setVal(col + row * 4, tally);
			}
		}
		return matrix3;
	}
	
	public static Matrix4d div(Matrix4d matrix1, Matrix4d matrix2){ return mul(matrix1, matrix2.inverse()); }
	
	public static Matrix4d add(Matrix4d matrix1, Matrix4d matrix2)
	{
		Matrix4d matrix3 = new Matrix4d();
		
		for(int row=0; row<4; row++)
			for(int col=0; col<4; col++)
				matrix3.setVal(row, col, matrix1.getRow(row).get(col) + matrix2.getRow(row).get(col));
		
		return matrix3;
	}
	
	public Matrix4d rotatePitch(double vol){ return mul(pitch(vol)); }
	public Matrix4d rotateYaw(double vol){ return mul(yaw(vol)); }
	public Matrix4d rotateRoll(double vol){ return mul(roll(vol)); }
	public Matrix4d rotate(Quaternion quat){ return mul(new Matrix4d(quat)); }
	
	public static Matrix4d pitch(double vol)
	{
		Matrix4d pitch = new Matrix4d();
		pitch.setVal(1, 1, Math.cos(vol));
		pitch.setVal(1, 2, Math.sin(vol));
		pitch.setVal(2, 1, -Math.sin(vol));
		pitch.setVal(2, 2, Math.cos(vol));
		return pitch;
	}
	
	public static Matrix4d yaw(double vol)
	{
		Matrix4d yaw = new Matrix4d();
		yaw.setVal(0, 0, Math.cos(vol));
		yaw.setVal(0, 2, -Math.sin(vol));
		yaw.setVal(2, 0, Math.sin(vol));
		yaw.setVal(2, 2, Math.cos(vol));
		return yaw;
	}
	
	public static Matrix4d roll(double vol)
	{
		Matrix4d roll = new Matrix4d();
		roll.setVal(0, 0, Math.cos(vol));
		roll.setVal(0, 1, -Math.sin(vol));
		roll.setVal(1, 0, Math.sin(vol));
		roll.setVal(1, 1, Math.cos(vol));
		return roll;
	}
	
	public Matrix4d translate(Vector3d vec){ return mul(translation(vec)); }
	public Matrix4d translate(double x, double y, double z)
	{
		return translate(new Vector3d(x, y, z));
	}
	
	public Matrix4d scale(double x){ return scale(x, x, x); }
	public Matrix4d scale(double x, double y, double z)
	{
		return mul(new Matrix4d(
						x, 0, 0,
						0, y, 0,
						0, 0, z));
	}
	
	/** Applies this matrix to the given vector as a translation */
	public Vector3d apply(Vector3d vec)
	{
		return mul(new Matrix4d().translate(vec)).toVec();
	}
	
	/** Converts this matrix to a position in local 3D space */
	public Vector3d toVec()
	{
		double x = getVal(0, 0) * getVal(0, 3);
		double y = getVal(1, 1) * getVal(1, 3);
		double z = getVal(2, 2) * getVal(2, 3);
		
		return new Vector3d(x, y, z);
	}
}
