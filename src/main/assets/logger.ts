namespace sbtts {
  export class Logger {
    logLevel: LogLevel;

    constructor(logLevel: LogLevel) {
      this.logLevel = logLevel;
    }

    debug(message: string) {
      if (this.logLevel === 'debug') {
        console.log(message);
      }
    }

    info(message: string) {
      switch(this.logLevel) {
        case 'debug':
        case 'info':
          console.log(message);
          break;
        default:
      }
    }

    warn(message: string) {
      switch(this.logLevel) {
        case 'debug':
        case 'info':
        case 'warn':
          console.log(message);
          break;
        default:

      }
    }

    error(message: string, error: any) {
      switch(this.logLevel) { // so, in every level for now...
        case 'debug':
        case 'info':
        case 'warn':
        case 'error':
          if (error !== undefined) {
            let errorMessage = error.message;
            if (error.fileName !== undefined) {
              errorMessage = `${errorMessage} in ${error.fileName}`;
            }
            if (error.lineNumber !== undefined) {
              errorMessage = `$errorMessage at line ${error.lineNumber}`;
            }
            console.log(`${message} ${errorMessage}`);
          } else {
            console.log(message);
          }
          break;
      }
    }
  }
}
