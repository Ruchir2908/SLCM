package com.example.myproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;

public class SignatureMainLayout extends LinearLayout implements View.OnClickListener {

    LinearLayout buttonsLayout;
    SignatureView signatureView;
    String fname;

    public SignatureMainLayout(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
//        this.align
//        this.setGravity(LinearLayout.CENTER);
        this.buttonsLayout = this.buttonsLayout();
        this.signatureView = new SignatureView(context);
        this.addView(this.buttonsLayout);
        this.addView(signatureView);
    }

    private LinearLayout buttonsLayout() {
        LinearLayout linearLayout = new LinearLayout(this.getContext());
        Button saveBtn = new Button(this.getContext());
        Button clearBtn = new Button(this.getContext());
//        Button eraseBtn = new Button(this.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundColor(Color.WHITE);
        saveBtn.setText("OK");
        saveBtn.setTag("Save");
        saveBtn.setBackground(getResources().getDrawable(R.drawable.border));
        saveBtn.setOnClickListener(this);
        clearBtn.setText("CANCEL");
        clearBtn.setTag("Clear");
        clearBtn.setBackground(getResources().getDrawable(R.drawable.border));
        clearBtn.setOnClickListener(this);
//        eraseBtn.setText("Erase");
//        eraseBtn.setTag("Erase");
//        eraseBtn.setOnClickListener(this);
        linearLayout.addView(saveBtn);
        linearLayout.addView(clearBtn);
//        linearLayout.addView(eraseBtn);
        return linearLayout;
    }

    @Override
    public void onClick(View v) {
        String tag = v.getTag().toString().trim();
        if (tag.equalsIgnoreCase("save")) {
            this.saveImage(this.signatureView.getSignature());
        }else if(tag.equalsIgnoreCase("erase")){
            this.signatureView.eraseImage();
        }else {
            this.signatureView.clearSignature();
        }
    }

    final void saveImage(Bitmap signature) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_signature");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        fname = "";

        SignActivity.Asignature = signature;

//            Intent intent = new Intent(getContext().getApplicationContext(),SaveActivity.class);
//            getContext().startActivity(intent);

//            AlertDialog.Builder saveFileBuilder = new AlertDialog.Builder(getContext());
//            saveFileBuilder
//                    .setView(R.layout.activity_save)
//                    .setTitle("ENTER FILE NAME: ")
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                EditText name = (EditText) getResources().getLayout().findViewById(R.id.fileName);
//                                fname = name.getText().toString();
//                            }
//                    })
//                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            signatureView.clearSignature();
//                        }
//                    });
//            AlertDialog saveFileDialog = saveFileBuilder.create();
//            saveFileDialog.show();



//            String fname = "signature.png";
        File file = new File(myDir, fname);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            signature.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(this.getContext(), "Signature saved.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class SignatureView extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private static final float ERASER_STROKE_WIDTH = 5f;
        private static final float HALF_ERASER_STROKE_WIDTH = ERASER_STROKE_WIDTH / 2;

        Boolean eraser = false;

        private Paint paint = new Paint();
        private Paint whitePaint = new Paint();
        private Path path = new Path();
        private Path whitePath = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public SignatureView(Context context) {

            super(context);

            paint.setAntiAlias(true);
            whitePaint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            whitePaint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            whitePaint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            whitePaint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
            whitePaint.setStrokeWidth(ERASER_STROKE_WIDTH);
            this.setBackgroundColor(Color.WHITE);
            this.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        }

        protected Bitmap getSignature() {
            Bitmap signatureBitmap = null;
            if (signatureBitmap == null) {
                signatureBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.RGB_565);
            }
            final Canvas canvas = new Canvas(signatureBitmap);
            this.draw(canvas);
            return signatureBitmap;
        }

        private void eraseImage(){
            if(eraser)
                eraser = false;
            else
                eraser = true;
        }

        private void clearSignature() {
            path.reset();
            this.invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if(eraser)
                canvas.drawPath(this.whitePath,this.whitePaint);
            else
                canvas.drawPath(this.path, this.paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    if(eraser)
                        whitePath.moveTo(eventX,eventY);
                    else
                        path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        if(eraser)
                            whitePath.lineTo(historicalX,historicalY);
                        else
                            path.lineTo(historicalX, historicalY);
                    }
                    if(eraser)
                        whitePath.lineTo(eventX,eventY);
                    else
                        path.lineTo(eventX, eventY);
                    break;

                default:

                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }

    }

}

