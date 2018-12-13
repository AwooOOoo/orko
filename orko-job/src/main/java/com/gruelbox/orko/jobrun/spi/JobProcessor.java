package com.gruelbox.orko.jobrun.spi;

import com.google.inject.Injector;

/**
 * The processing code for a {@link Job}.
 *
 * @author Graham Crockford
 * @param <T> The job type.
 */
public interface JobProcessor<T extends Job> {

  /**
   * Called when a job starts. In the event of the application being shut down and
   * the job killed, this may get called again to start the job the next time.
   *
   * @return {@link Status#RUNNING} or {@link Status#FAILURE_TRANSIENT} to stay
   *         resident, {@link Status#SUCCESS} or {@link Status#FAILURE_TRANSIENT}
   *         to finish (the latter two will cause a call to {@link #stop()}).
   */
  public Status start();

  /**
   * Called to terminate a job.  This may occur due to a shutdown, a loss of the
   * processing lock, or explicitly after a job calls
   * {@link JobControl#finish(Status)}.
   */
  public default void stop() {
    // default no--op
  }

  /**
   * A factory for {@link JobProcessor}s.
   *
   * @author Graham Crockford
   * @param <T> The job type.
   */
  public interface Factory<T extends Job> {
    public JobProcessor<T> create(T job, JobControl jobControl);
  }

  /**
   * Factory method for {@link JobProcessor}s.
   *
   * @param job The job to create a processor for.
   * @param jobControl The {@link JobControl} to pass to the processor.
   * @param injector The injector to create the required {@link JobProcessor.Factory}.
   * @return The {@link JobProcessor}.
   */
  @SuppressWarnings("unchecked")
  public static JobProcessor<Job> createProcessor(Job job, JobControl jobControl, Injector injector) {
    return ((JobProcessor.Factory<Job>) injector.getInstance(job.processorFactory())).create(job, jobControl);
  }
}