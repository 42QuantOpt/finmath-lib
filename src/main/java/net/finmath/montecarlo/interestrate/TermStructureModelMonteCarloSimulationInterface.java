/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christian-fries.de.
 *
 * Created on 23.12.2016
 */

package net.finmath.montecarlo.interestrate;

import java.time.LocalDateTime;

import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.MonteCarloSimulationInterface;
import net.finmath.montecarlo.model.AbstractModelInterface;
import net.finmath.montecarlo.process.AbstractProcessInterface;
import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.FloatingpointDate;

/**
 * @author Christian Fries
 *
 * @version 1.0
 */
public interface TermStructureModelMonteCarloSimulationInterface extends MonteCarloSimulationInterface {

	/**
	 * Return the forward rate for a given simulation time and a given period start and period end.
	 *
	 * @param date Simulation time
	 * @param periodStartDate Start time of period
	 * @param periodEndDate End time of period
	 * @return The forward rate as a random variable as seen on simulation time for the specified period.
	 * @throws net.finmath.exception.CalculationException Thrown if the valuation fails, specific cause may be available via the <code>cause()</code> method.
	 */
	default RandomVariableInterface getLIBOR(LocalDateTime date, LocalDateTime periodStartDate, LocalDateTime periodEndDate) throws CalculationException {
		LocalDateTime referenceDate = getReferenceDate();

		double time = FloatingpointDate.getFloatingPointDateFromDate(referenceDate, date);
		double periodStart = FloatingpointDate.getFloatingPointDateFromDate(referenceDate, periodStartDate);
		double periodEnd = FloatingpointDate.getFloatingPointDateFromDate(referenceDate, periodEndDate);

		return getLIBOR(time, periodStart, periodEnd);
	}

	/**
	 * Return the forward rate for a given simulation time and a given period start and period end.
	 *
	 * @param time          Simulation time
	 * @param periodStart   Start time of period
	 * @param periodEnd     End time of period
	 * @return 				The forward rate as a random variable as seen on simulation time for the specified period.
	 * @throws net.finmath.exception.CalculationException Thrown if the valuation fails, specific cause may be available via the <code>cause()</code> method.
	 */
	RandomVariableInterface getLIBOR(double time, double periodStart, double periodEnd) throws CalculationException;

	/**
	 * Return the numeraire at a given time.
	 *
	 * @param date Time at which the process should be observed
	 * @return The numeraire at the specified time as <code>RandomVariable</code>
	 * @throws net.finmath.exception.CalculationException Thrown if the valuation fails, specific cause may be available via the <code>cause()</code> method.
	 */
	default RandomVariableInterface getNumeraire(LocalDateTime date) throws CalculationException {
		double time = FloatingpointDate.getFloatingPointDateFromDate(getReferenceDate(), date);
		return getNumeraire(time);
	}

	/**
	 * Return the numeraire at a given time.
	 *
	 * @param time Time at which the process should be observed
	 * @return The numeraire at the specified time as <code>RandomVariable</code>
	 * @throws net.finmath.exception.CalculationException Thrown if the valuation fails, specific cause may be available via the <code>cause()</code> method.
	 */
	RandomVariableInterface getNumeraire(double time) throws CalculationException;

	/**
	 * Returns the underlying model.
	 *
	 * The model specifies the measure, the initial value, the drift, the factor loadings (covariance model), etc.
	 *
	 * @return The underlying model
	 */
	AbstractModelInterface getModel();

	/**
	 * @return The implementation of the process
	 */
	AbstractProcessInterface getProcess();

}
