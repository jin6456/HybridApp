package com.kiwoong.cmssign;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ImageLoad {


	public static  void imageLoad(Context context,File file ,ImageView imageView )
	{
		Picasso.with(context).load(file)
		.into(imageView);

	}
	public static  void imageLoad(Context context,String imagePath ,ImageView imageView )
	{
		if ( !imagePath .contains("http") )
		{
			imagePath = HttpConnect.HOST + imagePath ;
		}
		Picasso.with(context).load(Uri.parse(imagePath))
		.into(imageView);

	}

	
	public static  void imageLoadSkipCash(Context context,String imagePath ,ImageView imageView )
	{
		if ( !imagePath .contains("http") )
		{
			imagePath = HttpConnect.HOST + imagePath ;
		}
		Picasso.with(context).load(Uri.parse(imagePath)).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)
		.into(imageView);

	}
}
