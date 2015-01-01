/**
 * Created by robertdunn on 12/23/14.
 * Singleton for all classes to call if there was an error getting data
 */
public class ErrorThrower
{
  private static ErrorThrower instance = new ErrorThrower();
  private ErrorStateChangedListener notifier;

  public static ErrorThrower inst()
  {
    return instance;
  }

  private ErrorThrower()
  {
  }

  public void setErrorChangedListener(ErrorStateChangedListener listener)
  {
    notifier = listener;
  }

  public void setConnectionError(boolean isConnectionError)
  {
    if(notifier != null)
      notifier.errorStateChanged(isConnectionError);
  }

}
