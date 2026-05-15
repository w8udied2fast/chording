package com.w8udied2fast.chording.ui.chords;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.w8udied2fast.chording.R;
import com.w8udied2fast.chording.data.model.Chord;

import java.util.List;

public class ChordDiagramView extends View {
    private Paint stringPaint, fretPaint, nutPaint, dotPaint, textPaint, oxPaint;
    private Chord chord;

    private float padding;
    private float stringSpacing;
    private float fretSpacing;
    private float topOffset;
    private float dotRadius;
    private int maxFretsToShow = 5;
    private int startFret = 1;
    private boolean dimensionsCalculated = false;

    public ChordDiagramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaints(context);
    }

    private void initPaints(Context context) {
        int primaryColor = context.getResources().getColor(R.color.green_primary, context.getTheme());
        int onSurfaceColor = context.getResources().getColor(R.color.on_surface, context.getTheme());

        stringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        stringPaint.setColor(primaryColor);
        stringPaint.setStrokeWidth(4f);
        stringPaint.setStyle(Paint.Style.STROKE);

        fretPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fretPaint.setColor(primaryColor);
        fretPaint.setStrokeWidth(2f);
        fretPaint.setStyle(Paint.Style.STROKE);

        nutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        nutPaint.setColor(primaryColor);
        nutPaint.setStrokeWidth(8f);
        nutPaint.setStyle(Paint.Style.STROKE);

        dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint.setColor(primaryColor);
        dotPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(onSurfaceColor);
        textPaint.setTextSize(40f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        oxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oxPaint.setColor(onSurfaceColor);
        oxPaint.setTextSize(36f);
        oxPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDimensions();
        dimensionsCalculated = true;
        invalidate();
    }

    public void setChord(Chord chord) {
        this.chord = chord;
        // Пересчитываем размеры, если view уже имеет размер
        if (getWidth() > 0 && getHeight() > 0) {
            calculateDimensions();
            dimensionsCalculated = true;
        }
        invalidate();
    }

    private void calculateDimensions() {
        if (chord == null || chord.getPositions() == null) return;

        float width = getWidth();
        float height = getHeight();

        padding = width * 0.05f;
        topOffset = height * 0.1f;
        dotRadius = Math.min(width, height) * 0.04f;

        float availableWidth = width - padding * 2;
        float availableHeight = height - padding * 2 - topOffset;

        stringSpacing = availableWidth / 5f; // 6 струн и 5 промежутков
        fretSpacing = availableHeight / maxFretsToShow;

        // Находим минимальный и максимальный лад
        int minFret = Integer.MAX_VALUE;
        int maxFret = 0;
        for (Chord.Position p : chord.getPositions()) {
            if (p.getFret() > 0) {
                minFret = Math.min(minFret, p.getFret());
                maxFret = Math.max(maxFret, p.getFret());
            }
        }

        // если maxFret > 5, сдвигаем гриф к началу аккорда
        if (maxFret > 5 && minFret != Integer.MAX_VALUE) {
            startFret = Math.max(1, minFret); // Начинаем на лад раньше для удобства
        } else {
            startFret = 1; // Иначе начинаем с 1-го лада
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (chord == null) return;

        // Если размеры ещё не посчитаны, то пробуем посчитать
        if (!dimensionsCalculated && getWidth() > 0 && getHeight() > 0) {
            calculateDimensions();
            dimensionsCalculated = true;
        }

        if (!dimensionsCalculated) return;

        drawGrid(canvas);
        drawOXMarkers(canvas);
        drawFretIndicator(canvas);
        drawFingerPositions(canvas);
    }

    private void drawGrid(Canvas canvas) {
        // Вертикально - струны (6 струн: слева шестаая, справа первая)
        for (int i = 0; i < 6; i++) {
            float x = padding + i * stringSpacing;
            canvas.drawLine(x, topOffset, x, topOffset + maxFretsToShow * fretSpacing, stringPaint);
        }
        // Горизонтально - лады
        for (int i = 0; i <= maxFretsToShow; i++) {
            float y = topOffset + i * fretSpacing;
            canvas.drawLine(padding, y, padding + 5 * stringSpacing, y, i == 0 ? nutPaint : fretPaint);
        }
    }

    private void drawOXMarkers(Canvas canvas) {
        for (int s = 6; s >= 1; s--) {
            Chord.Position pos = getPositionForString(s);
            float x = padding + (6 - s) * stringSpacing;
            float y = topOffset / 2f + 10f;

            if (pos == null) {
                canvas.drawText("X", x, y, oxPaint);
            } else if (pos.getFret() == 0) {
                canvas.drawText("O", x, y, oxPaint);
            }
        }
    }

    private void drawFretIndicator(Canvas canvas) {
        // Показываем номер лада только если startFret > 1
        if (startFret > 1) {
            float x = padding - 25f;
            float y = topOffset * 1.1f;
            textPaint.setTextSize(54f);
            canvas.drawText(String.valueOf(startFret), x, y, textPaint);
        }
    }

    private void drawFingerPositions(Canvas canvas) {
        for (Chord.Position pos : chord.getPositions()) {
            if (pos.getFret() == 0) continue;

            int relativeFret = pos.getFret() - startFret;
            if (relativeFret < 0 || relativeFret >= maxFretsToShow) continue;

            float x = padding + (6 - pos.getString()) * stringSpacing;
            float y = topOffset + relativeFret * fretSpacing + fretSpacing / 2f;

            canvas.drawCircle(x, y, dotRadius, dotPaint);

            if (pos.getFinger() > 0) {
                textPaint.setTextSize(dotRadius * 0.9f);
                canvas.drawText(String.valueOf(pos.getFinger()),
                        x, y + dotRadius * 0.3f, textPaint);
            }
        }
    }

    private Chord.Position getPositionForString(int stringNum) {
        if (chord.getPositions() == null) return null;
        for (Chord.Position p : chord.getPositions()) {
            if (p.getString() == stringNum) return p;
        }
        return null;
    }
}
