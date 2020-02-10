declare namespace sbtts {
  type LogLevel = 'debug' | 'info' | 'warn' | 'error';

  interface Options {
    logLevel: LogLevel;
    sources: string[];
    sourceRoot: string;
    baseUrl: string;
    rootDir: string;
    configFiles: string[];
    projectBase: string;
  }

  interface Map {
    [key: string]: string;
  }
}
