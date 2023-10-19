package com.example.photoeditor.Classs;

import android.graphics.RectF;

import com.example.photoeditor.ModelClass.PolishLayout;
import com.example.photoeditor.ModelClass.PolishLine;

public class PolishLayoutParser {
    private PolishLayoutParser() {
    }

    public static PolishLayout parse(final PolishLayout.Info info) {
        PolishLayout polishLayout;
        if (info.type == 0) {
            polishLayout = new StraightCollageLayout() {
                /* class com.example.photoareditor.Classs.PolishLayoutParser.AnonymousClass1 */

                @Override // com.example.photoareditor.ModelClass.PolishLayout
                public PolishLayout clone(PolishLayout polishLayout) {
                    return null;
                }

                @Override // com.example.photoareditor.ModelClass.PolishLayout, com.example.photoareditor.Classs.StraightCollageLayout
                public void layout() {
                    int size = info.steps.size();
                    int i = 0;
                    for (int i2 = 0; i2 < size; i2++) {
                        PolishLayout.Step step = info.steps.get(i2);
                        int i3 = step.type;
                        if (i3 == 0) {
                            addLine(step.position, step.lineDirection(), info.lines.get(i).getStartRatio());
                        } else if (i3 == 1) {
                            addCross(step.position, step.hRatio, step.vRatio);
                        } else if (i3 == 2) {
                            cutAreaEqualPart(step.position, step.hSize, step.vSize);
                        } else if (i3 == 3) {
                            cutAreaEqualPart(step.position, step.part, step.lineDirection());
                        } else if (i3 == 4) {
                            cutSpiral(step.position);
                        }
                        i += step.numOfLine;
                    }
                }
            };
        } else {
            polishLayout = new SlantCollageLayout() {
                /* class com.example.photoareditor.Classs.PolishLayoutParser.AnonymousClass2 */

                @Override // com.example.photoareditor.ModelClass.PolishLayout
                public PolishLayout clone(PolishLayout polishLayout) {
                    return null;
                }

                @Override // com.example.photoareditor.ModelClass.PolishLayout, com.example.photoareditor.Classs.SlantCollageLayout
                public void layout() {
                    int size = info.steps.size();
                    for (int i = 0; i < size; i++) {
                        PolishLayout.Step step = info.steps.get(i);
                        int i2 = step.type;
                        if (i2 == 0) {
                            addLine(step.position, step.lineDirection(), info.lines.get(i).getStartRatio(), info.lines.get(i).getEndRatio());
                        } else if (i2 == 1) {
                            addCross(step.position, 0.5f, 0.5f, 0.5f, 0.5f);
                        } else if (i2 == 2) {
                            cutArea(step.position, step.hSize, step.vSize);
                        }
                    }
                }
            };
        }
        polishLayout.setOuterBounds(new RectF(info.left, info.top, info.right, info.bottom));
        polishLayout.layout();
        polishLayout.setColor(info.color);
        polishLayout.setRadian(info.radian);
        polishLayout.setPadding(info.padding);
        int size = info.lineInfos.size();
        for (int i = 0; i < size; i++) {
            PolishLayout.LineInfo lineInfo = info.lineInfos.get(i);
            PolishLine polishLine = polishLayout.getLines().get(i);
            polishLine.startPoint().x = lineInfo.startX;
            polishLine.startPoint().y = lineInfo.startY;
            polishLine.endPoint().x = lineInfo.endX;
            polishLine.endPoint().y = lineInfo.endY;
        }
        polishLayout.sortAreas();
        polishLayout.update();
        return polishLayout;
    }
}