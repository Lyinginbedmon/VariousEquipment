package com.lying.variousequipment.client.model;

import net.minecraft.util.NonNullList;
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
				row.set(i, getVal(i + index * 4));
		return row;
	}
	
	public NonNullList<Double> getColumn(int index)
	{
		index %= 4;
		NonNullList<Double> column = NonNullList.<Double>withSize(4, 0D);
		if(index <= 3)
			for(int i=0; i<4; i++)
				column.set(i, this.values.get(index + i * 4));
		
		return column;
	}
	
	public double getVal(int index){ return this.values.get(index % this.values.size()); }
	public double getVal(int row, int col){ return getVal(row + col * 4); }
	
	public void setVal(int index, double val){ this.values.set(index, val); }
	public void setVal(int row, int col, double val){ setVal(row + col * 4, val); }
	
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
	
	public static Matrix4d mul(Matrix4d matrix1, Matrix4d matrix2)
	{
		Matrix4d matrix3 = new Matrix4d();
		for(int row=0; row<4; row++)
		{
			NonNullList<Double> rowVals = matrix1.getRow(row);
			for(int col=0; col<4; col++)
			{
				NonNullList<Double> colVals = matrix2.getColumn(col);
				
				int tally = 0;
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
	
	/** Rotates the matrix in radians */
	public Matrix4d rotate(double pitch, double yaw, double roll)
	{
		// TODO
		return this;
	}
	
	public Matrix4d translate(Vector3d vec){ return translate(vec.x, vec.y, vec.z); }
	public Matrix4d translate(double x, double y, double z)
	{
		Matrix4d matrix = new Matrix4d();
		matrix.setVal(0, 3, x);
		matrix.setVal(1, 3, y);
		matrix.setVal(2, 3, z);
		return mul(matrix);
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
		Matrix4d translated = mul(new Matrix4d().translate(vec));
		double x = translated.getVal(0, 0) * translated.getVal(0, 3);
		double y = translated.getVal(1, 1) * translated.getVal(1, 3);
		double z = translated.getVal(2, 2) * translated.getVal(2, 3);
		
		return new Vector3d(x, y, z);
	}
}
