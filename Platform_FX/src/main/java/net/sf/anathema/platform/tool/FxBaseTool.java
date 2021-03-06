package net.sf.anathema.platform.tool;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import net.sf.anathema.interaction.AcceleratorMap;
import net.sf.anathema.interaction.Command;
import net.sf.anathema.interaction.CommandProxy;
import net.sf.anathema.interaction.Hotkey;
import net.sf.anathema.interaction.ProxyAcceleratorMap;
import net.sf.anathema.interaction.Tool;
import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.platform.fx.FxComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FxBaseTool implements Tool, FxComponent {
  private final ButtonBase button;
  private final ImageView overlay;
  private final List<ImageClosure> onLoad = new ArrayList<>();
  private final ProxyAcceleratorMap acceleratorMap = new ProxyAcceleratorMap();
  private final CommandProxy command = new CommandProxy();

  public FxBaseTool(final ButtonBase button, ImageView overlay, ImageClosure... actionsOnLoad) {
    this.button = button;
    this.overlay = overlay;
    Collections.addAll(onLoad, actionsOnLoad);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        button.setOnAction(new Execute(command));
      }
    });
  }

  @Override
  public void setIcon(final RelativePath relativePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        ImageContainer image = new LoadImage(relativePath).run();
        for (ImageClosure action : onLoad) {
          action.run(image);
        }
      }
    });
  }


  @Override
  public void setOverlay(final RelativePath relativePath) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        ImageContainer image = new LoadImage(relativePath).run();
        new SetImage(overlay).run(image);
      }
    });
  }

  @Override
  public void setTooltip(final String text) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        button.setTooltip(new Tooltip(text));
      }
    });
  }

  @Override
  public void setText(final String text) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        button.setText(text);
      }
    });
  }

  @Override
  public void enable() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        button.setDisable(false);
      }
    });
  }

  @Override
  public void disable() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        button.setDisable(true);
      }
    });
  }

  @Override
  public void setCommand(final Command command) {
    this.command.setDelegate(command);
  }

  @Override
  public void setHotkey(Hotkey key) {
    acceleratorMap.register(key, command);
  }

  public void registerHotkeyIn(AcceleratorMap actualMap) {
    acceleratorMap.setActualMap(actualMap);
  }

  @Override
  public Node getNode() {
    return button;
  }
}