package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.drivers.adapter.AbstractDriverAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.Job2dDriverAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;
import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;

public class TestJobs2dPatterns {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Setup test concerning preset figures in context.
	 * 
	 * @param application Application context.
	 */
	private static void setupPresetTests(Application application) {
		SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager());
		
		SelectTestFigure2OptionListener selectTestFigure2OptionListener = new SelectTestFigure2OptionListener(DriverFeature.getDriverManager());
		SelectTestJaneFigureOptionListener selectTestJaneFigureOptionListener = new SelectTestJaneFigureOptionListener(DriverFeature.getDriverManager());
		SelectTestTriangleOptionListener selectTestTriangleOptionListener = new SelectTestTriangleOptionListener(DriverFeature.getDriverManager());
		SelectTestRectangleOptionListener selectTestRectangleOptionListener = new SelectTestRectangleOptionListener(DriverFeature.getDriverManager());
		SelectTestTrapeziumOptionListener selectTestTrapeziumOptionListener = new SelectTestTrapeziumOptionListener(DriverFeature.getDriverManager());

		application.addTest("Figure Joe 1", selectTestFigureOptionListener);
		application.addTest("Figure Joe 2", selectTestFigure2OptionListener);
		application.addTest("Figure Jane", selectTestJaneFigureOptionListener);
		application.addTest("triangle",selectTestTriangleOptionListener);
		application.addTest("rectangle",selectTestRectangleOptionListener);
		application.addTest("trapezium",selectTestTrapeziumOptionListener);
	}

	/**
	 * Setup driver manager, and set default driver for application.
	 * 
	 * @param application Application context.
	 */
	private static void setupDrivers(Application application) {
		Job2dDriver loggerDriver = new LoggerDriver();
		DriverFeature.addDriver("Logger Driver", loggerDriver);
		DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);

		Job2dDriver testDriver = new Job2dDriverAdapter();
		DriverFeature.addDriver("Buggy Simulator", testDriver);
		
		Job2dDriver specialAdapter = new LineDrawerAdapter();
		DriverFeature.addDriver("Special Simulator", specialAdapter);
		
		Job2dDriver abstractDriverAdapter = new AbstractDriverAdapter();
		DriverFeature.addDriver("Jane Simulator", abstractDriverAdapter);

		DriverFeature.updateDriverInfo();
	}

	/**
	 * Auxiliary routines to enable using Buggy Simulator.
	 * 
	 * @param application Application context.
	 */
//	private static void setupDefaultDrawerVisibilityManagement(Application application) {
//		DefaultDrawerFrame defaultDrawerWindow = DefaultDrawerFrame.getDefaultDrawerFrame();
//		application.addComponentMenuElementWithCheckBox(DrawPanelController.class, "Default Drawer Visibility",
//				new SelectChangeVisibleOptionListener(defaultDrawerWindow), true);
//		defaultDrawerWindow.setVisible(true);
//	}

	/**
	 * Setup menu for adjusting logging settings.
	 * 
	 * @param application Application context.
	 */
	private static void setupLogger(Application application) {
		application.addComponentMenu(Logger.class, "Logger", 0);
		application.addComponentMenuElement(Logger.class, "Clear log",
				(ActionEvent e) -> application.flushLoggerOutput());
		application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
		application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
		application.addComponentMenuElement(Logger.class, "Warning level",
				(ActionEvent e) -> logger.setLevel(Level.WARNING));
		application.addComponentMenuElement(Logger.class, "Severe level",
				(ActionEvent e) -> logger.setLevel(Level.SEVERE));
		application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Application app = new Application("2d jobs Visio");
				DrawerFeature.setupDrawerPlugin(app);

				DriverFeature.setupDriverPlugin(app);
				setupDrivers(app);
				setupPresetTests(app);
				setupLogger(app);
				
				SetPositionCommand setPositionCommand = new SetPositionCommand(new LoggerDriver(),5, 5);
				setPositionCommand.execute();

				app.setVisibility(true);
			}
		});
	}

}
